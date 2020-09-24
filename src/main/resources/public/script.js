const URL = 'http://localhost:8081';
let entries = [];
let roles = [];
let categories = [];
var sJWT = undefined;
var oUser = undefined;

const dateAndTimeToDate = (dateString, timeString) => {
    return new Date(`${dateString}T${timeString}`).toISOString();
};

//login stuff
const loginHandler = (e) => {
    e.preventDefault();
    const oFormData = new FormData(e.target);
    const oLogin = {};
    oLogin['username'] = oFormData.get('username');
    oLogin['password'] = oFormData.get('password');

    fetch(`${URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(oLogin)
    }).then((result) => {
        sJWT = undefined;
        result.headers.forEach(function(item, index){
            sJWT = item.toString().match(/^Bearer/) !== null ? item : sJWT;
        });
        if (checkJWT()){
            fetch(`${URL}/users/username/` + oLogin.username, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': sJWT
                }
            }).then((result) => {
                result.json().then((result) => {
                    oUser = result;
                });
            });

            loadEntryPage();
        }
    });

};

const loadLoginPage = () => {
    var oLoginPage = document.querySelector('#loginPage');
    oLoginPage.style.display = "inline-block";

    var oSignUpPage = document.querySelector('#signUpPage');
    oSignUpPage.style.display = "none";

    var oEntryPage = document.querySelector('#createEntryPage');
    oEntryPage.style.display = "none";

    var oUserPage = document.querySelector('#userPage');
    oUserPage.style.display = "none";
}







//sign up stuff
const loadSignUpPage = (e) => {
    var oLoginPage = document.querySelector('#loginPage');
    oLoginPage.style.display = "none";

    var oSignUpPage = document.querySelector('#signUpPage');
    oSignUpPage.style.display = "inline-block";

    var oEntryPage = document.querySelector('#createEntryPage');
    oEntryPage.style.display = "none";

    var oUserPage = document.querySelector('#userPage');
    oUserPage.style.display = "none";

    fetch(`${URL}/roles`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((result) => {
        result.json().then((result) => {
            roles = result;
            renderRoles(document.querySelector('#signUpRole'));
        });
    });
};

const signUpHandler = (e) => {
    e.preventDefault();
    const oFormData = new FormData(e.target);
    const oSignUp = {};
    oSignUp['username'] = oFormData.get('username');
    oSignUp['password'] = oFormData.get('password');
    oSignUp['email'] = oFormData.get('email');
    oSignUp['employeeId'] = oFormData.get('employeeId');
    oSignUp['firstname'] = oFormData.get('firstname');
    oSignUp['lastname'] = oFormData.get('lastname');
    oSignUp['role'] = {"id" : oFormData.get('role')};

    fetch(`${URL}/users/sign-up`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(oSignUp)
    }).then((result) => {
        if (result.status === 201){
            loadLoginPage();
        }
    });
};

const renderRoles = (oSelect) => {
    oSelect.innerHTML = '';
    roles.forEach((role) => {
        oSelect.appendChild(createOption(role.roleName + ": " + (role.admin ? "Admin" : "Kein Admin"), role.id));
    });
};






//entry stuff
const renderCategories = (oSelect) => {
    oSelect.innerHTML = '';
    categories.forEach((category) => {
        oSelect.appendChild(createOption(category.categoryName, category.id));
    });
}

const loadEntryPage = () => {
    var oLoginPage = document.querySelector('#loginPage');
    oLoginPage.style.display = "none";

    var oSignUpPage = document.querySelector('#signUpPage');
    oSignUpPage.style.display = "none";

    var oEntryPage = document.querySelector('#createEntryPage');
    oEntryPage.style.display = "inline-block";

    var oUserPage = document.querySelector('#userPage');
    oUserPage.style.display = "none";

    if (!checkJWT())
        loadLoginPage();

    fetch(`${URL}/categories`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': sJWT
        }
    }).then((result) => {
        result.json().then((result) => {
            categories = result;
            renderCategories(document.querySelector('#entryCategory'));
        });
    });

    indexEntries();
};

const createEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const entry = {};
    var oStartTime = new Date(dateAndTimeToDate(formData.get('startTimeDate'), formData.get('startTimeTime')));
    oStartTime.setHours(oStartTime.getHours() + oStartTime.getTimezoneOffset()/60*(-1));
    entry['startTime'] = oStartTime.toJSON();
    var oEndTime = new Date(dateAndTimeToDate(formData.get('endTimeDate'), formData.get('endTimeTime')));
    oEndTime.setHours(oEndTime.getHours() + oEndTime.getTimezoneOffset()/60*(-1));
    entry['endTime'] = oEndTime.toJSON();
    entry['category'] = {id: parseInt(formData.get('category'))};

    if (!checkJWT())
        loadLoginPage();

    fetch(`${URL}/entries`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': sJWT
        },
        body: JSON.stringify(entry)
    }).then((result) => {
        result.json().then((entry) => {
            categories.forEach(function(item){
                if (item.id == entry.category.id)
                    entry.category = item;
            });
            entries.push(entry);
            renderEntries();
        });
    });
};

const indexEntries = () => {
    if (!checkJWT())
        loadLoginPage();

    fetch(`${URL}/entries`, {
        method: 'GET',
        headers: {
            Authorization: sJWT
        }
    }).then((result) => {
        result.json().then((result) => {
            entries = result;
            renderEntries();
        });
    });
    renderEntries();
};

const renderEntries = () => {
    const display = document.querySelector('#entryDisplay');
    display.innerHTML = '';
    entries.forEach((entry) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(entry.id));
        row.appendChild(createCell(entry.category.categoryName));
        row.appendChild(createCell(new Date(entry.startTime).toLocaleString()));
        row.appendChild(createCell(new Date(entry.endTime).toLocaleString()));
        row.appendChild(createCell(entry.user.username));
        row.appendChild(createButton("Löschen", function(){
            if (!checkJWT())
                loadLoginPage();

            fetch(`${URL}/entries/` + entry.id, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': sJWT
                }
            }).then((result) => {
                indexEntries();
            });
        }));
        display.appendChild(row);
    });
};






//user stuff
/*const showUserPage = (e) => {
    var oLoginPage = document.querySelector('#loginPage');
    oLoginPage.style.display = "none";

    var oSignUpPage = document.querySelector('#signUpPage');
    oSignUpPage.style.display = "none";

    var oEntryPage = document.querySelector('#createEntryPage');
    oEntryPage.style.display = "none";

    var oUserPage = document.querySelector('#userPage');
    oUserPage.style.display = "inline-block";

    renderUserPage();
};

const renderUserPage = () => {
    if (oUser === undefined)
        loadLoginPage();

    var oUserForm = document.querySelector('#userForm');




};

*/




//general
document.addEventListener('DOMContentLoaded', function(){
    const loginForm = document.querySelector('#loginForm');
    loginForm.addEventListener('submit', loginHandler);

    const signUpForm = document.querySelector('#signUpForm');
    signUpForm.addEventListener('submit', signUpHandler);

    const createEntryForm = document.querySelector('#createEntryForm');
    createEntryForm.addEventListener('submit', createEntry);



    const signUpButton = document.querySelector('#signupButton');
    signUpButton.addEventListener('click', loadSignUpPage)
/*
    const showUserButton = document.querySelector('#showUserButton');
    showUserButton.addEventListener('click', showUserPage)*/
});

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

const createButton = (text, callback) => {
    const button = document.createElement('Button');
    button.innerHTML = text;
    button.onclick = callback;
    return button;
}

const createOption = (text, id) => {
    const option = document.createElement('option');
    option.value = id;
    option.innerText = text;
    return option;
};

const checkJWT = () => {
    return sJWT !== undefined;
}