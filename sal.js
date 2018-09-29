var urlbase = "http://localhost:8081/ISD_Library/webresources/";
var urlpart = "//" + window.location.host + "/FrontEnd";

// ---------------------------------------------------- USERS ---------------------------------- //
function getAllUsers(cb) {
	var url = urlbase + "users";
	makeCorsRequest(url, "GET", function(response) {
		cb(response);
	});
}

function getUserById(userId, cb){
	var url = urlbase + "users/" + userId;
	makeCorsRequest(url, "GET", function(response) {
		cb(response);
	});
}
function showUser(id){
	$(location).attr('href',urlpart + "/user.html?id=" + id);
}

function deleteUser(userId){
	var ans = confirm("Are you for real?");
	if(ans){
		var url = urlbase + "users/" + userId;
		makeCorsRequest(url, "DELETE", function(response){
			location.reload();
		});
	}
}

function updateUser(userId){
	var form = $(".form-user");
	var user = getFormData(form);
	if(validateUserData(user,2)){
		user = JSON.stringify(user);
		makeCorsRequest(urlbase + "users/" + userId, "PUT", function(response) {
		 	showUser(userId);
		},user);
	}
}
function updatePageUser(id){
	$(location).attr('href', urlpart + "/user-edit.html?id=" + id);
}

function addUser(){
	var form = $(".form-user");
	var user = getFormData(form);
	if(validateUserData(user)){
		user = JSON.stringify(user);
		makeCorsRequest(urlbase + "users", "POST", function(response) {
			var url = urlpart + "/user-table.html";
			$(location).attr('href',url);
		},user);
	}
}

function logoutUser(){
	$.removeCookie("username",{ path: '/' });
	$.removeCookie("password",{ path: '/' });
	$.removeCookie("role",{ path: '/' });
	var url = urlpart + "/";
	$(location).attr('href',url);
}

function loginUser(username ,password){
	var url = urlbase + "auth/" + username;
	makeCorsRequest(url, "GET", function(response) {
		$.cookie("role",response.Role, {path: '/' });
		showAllBooks();
	});
}
// ---------------------------------------------------- BOOKS ---------------------------------- //
function getAllBooks(cb,title) {
	var add = "";
	if(title != undefined) add = "?title=" + title;
	var url = urlbase + "books"+add;
	var method = "GET";
	makeCorsRequest(url, method, function(response) {
		cb(response);
	});
}
function showAllBooks(){
	$(location).attr('href',urlpart + "/book-table.html");
};


function getBookById(id_book, cb){
	var url = urlbase + "books/" + id_book;
	makeCorsRequest(url, "GET", function(response) {
		cb(response);
	});
}
function showBook(id){
	$(location).attr('href',urlpart + "/book.html?id=" + id);
}

function showBookByTitle(){
	var title = $("#search-book").val();
	var add = "";
	if(title != "") add = "?title="+ title
		var url = urlpart + "/book-table.html" + add;
	$(location).attr('href',url);
}

function deleteBook(id_book){
	var ans = confirm("Don't do this :(");
		if(ans){
		var url = urlbase + "books/" + id_book;
		makeCorsRequest(url, "DELETE", function(response){
			location.reload();
		});
	}
}

function updateBook(id_book, cb){
	var form = $(".form-book");
	var url = urlbase + "books/" + id_book;
	var book = JSON.stringify((getFormData(form)));
	makeCorsRequest(url, "PUT", function(response) {
		var url = urlpart + "/book-table.html";
			$(location).attr('href',url);
	},book);
}
function updatePageBook(id){
	var url = urlpart + "/book-edit.html?id=" + id;
	$(location).attr('href',url);
}

function addBook(cb){
	var form = $(".form-book");
	var book = getFormData(form);
	var category = {};
	$.each(book.categories,function(index,categ){
		book.categories[index] = jQuery.parseJSON( '{ "id_category": '+ categ +' }' );
	});
	book = JSON.stringify(book);
	console.log(book);
	makeCorsRequest(urlbase + "books", "POST", function(response) {
		console.log(response);
		var url = urlpart + "/book-table.html";
			$(location).attr('href',url);
		//cb(response);
	},book);
}

function borrowBook(bookId, statusId){
	var loan = {};
	loan.idBook = bookId;
	loan.status = statusId;
	loan = JSON.stringify(loan);
	makeCorsRequest(urlbase + "loans", "POST", function(response){
		cb(response);
	},loan);
}

function getBooksByCateg(idCategory,cb){
	makeCorsRequest(urlbase + "books?categ=" + idCategory, "GET", function(books){
		 cb(books);
	});
}
function showBooksByCategory(idCategory){
	var url = urlpart + "/book-table.html?categ=" + idCategory;
	$(location).attr('href',url);
}

// ---------------------------------------------------- CATEGORIES ---------------------------------- //
function getAllCateg(cb) {
	var url = urlbase + "categs";
	var method = "GET";
	makeCorsRequest(url, method, function(response) {
		cb(response);
	});
}

function deleteCategory(id_category){
	var ans = confirm("u sure?");
	if(ans){
	var url = urlbase + "categs/" + id_category;
	makeCorsRequest(url, "DELETE", function(response){
		location.reload();

	});
}
}

function addCategory(cb){
	var form = $(".form-category");
	var url = urlbase + "categs";
	var category = JSON.stringify((getFormData(form)));
	makeCorsRequest(url, "POST", function(response) {
		var url = urlpart + "/category-table.html";
			$(location).attr('href',url);
		cb(response);
	},category);
}

// ---------------------------------------------------- LOANS ---------------------------------- //
function getAllLoans(cb){
	var url = urlbase + "loans";
	makeCorsRequest(url, "GET", function(response){
		cb(response);
	});
}

function deleteLoanById(id_loan, cb){
	var url = urlbase + "loans/" + id_loan;
	makeCorsRequest(url, "DELETE", function(response){
		location.reload();
	});
}

function updateLoan(loanId, bookId){
	var stat = $(".form-control" + loanId).val();
	var loan = {status: stat, idBook:bookId};
	var url = urlbase + "loans/" + loanId;
	var loan = JSON.stringify(loan);
	console.log(loan + loanId);
	makeCorsRequest(url, "PUT", function(){},loan);
}

function insertLoan(cb){
	var form = $(".form-assign");
	var url = urlbase + "loans";
	var loan = JSON.stringify((getFormData(form)));
	makeCorsRequest(url, "POST", function(response){
		var url = urlpart + "/loan-table.html";
			$(location).attr('href',url);
		// cb(response);
	},loan);
}

// ---------------------------------------------------- GENERAL ---------------------------------- //
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;
    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function getFormData(form){
	var fields = {};
	form.find(":input").each(function() {
	 	fields[$(this).attr('id')] = $(this).val();
	});
	var obj = {fields: fields};
	return obj.fields;
}

function makeCorsRequest(url, method, cb, data) {
	var xhr = new XMLHttpRequest();
	var user = $.cookie("username");
	var pass = $.cookie("password");
	xhr.open(method, url, true);
	xhr.withCredentials = true;
	xhr.setRequestHeader("Authorization", "Basic " + btoa(user + ":" + pass));
	xhr.setRequestHeader("Content-type", "application/json");

	xhr.onload = function() {
		var response = $.parseJSON(xhr.responseText);
		if(response.code > 399){
			errorResponse(response);
		}else{
			cb(response);
		}
	};

	xhr.onerror = function() {
		alert('Woops, there was an error making the request.');
	};

	if(method == "GET"){
		xhr.send();
	}else{
		xhr.send(data);
	}
}

function isEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var verif = regex.test(email);
	if(!verif) $.notify("Incorrect email format","error");
	return verif;
}

function isLenght(obj){
	if((obj.value).length < 5){
		$.notify("Min " + obj.name +" length is 5","error");
		return false;
	}else if((obj.value).length > 15){
		$.notify("Max " + obj.name +" length is 10","error");
		return false;
	}
	return true;
}

function isNotEmpty(obj){
	if((obj.value).length < 1){
		$.notify(obj.name +" must not be empty","error");
		return false;
	}
	return true;
}

function validateUserData(user, type){
	var pass = { value : user.password, name : "Password" };
	var uname = { value : user.username, name : "Username" };
	var fname = { value : user.firstname, name : "Firstname" };
	var lname = { value : user.lastname, name : "Lastname" };
	var email = { value : user.email, name : "Email"};
	if(type == 2) pass.value = "123456";
	if(isNotEmpty(uname) && isNotEmpty(fname) && isNotEmpty(lname) && isNotEmpty(email)
		&& isEmail(user.email) && isNotEmpty(pass) && isLenght(pass) && isLenght(uname))
			return true;
	return false;
}

function errorResponse(response){
	if(response.code == 401){
		$.removeCookie("username",{ path: '/' });
		$.removeCookie("password",{ path: '/' });
	}
	$.notify(response.code + " " + response.message);
}
