<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
    xmlns:th="http://www.thymeleaf.org"
    xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3"
    xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="index.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="appointments.js"></script>
</head>
<body>
	<h1 id="appointmentHeader"> Appointments Page </h1>
<div class="container">
  <div class="row">
    <div class="col-md-4">
<form action="/newAppointment.pl" onsubmit="return newAppointment()" method="post">
  <div id="newButtonDiv" class="col-md-4" style="margin-right:110px;"><input id="newButton" type="submit" class="btn btn-primary" value="New"></div>
  <div id="inputFields">
  <button id="cancelButton"  type="button" class="btn btn-danger">Cancel</button>
  <br />
  <label for="date">Date:</label>
  <input id="appointmentDate" type="text" class="form-control" id="date">
    <label for="time">Time:</label>
  <input id="appointmentTime" type="text" class="form-control" id="time">
      <label for="description">DESC:</label>
  <input id="description" type="text" class="form-control" id="description">
  </div>
</form>
</div>
  </div>
</div>

<div class="container">
  <div class="row">
    <div class="col-md-4">
	<div class="form-group">
  <label for="usr">Search:</label>
  <input id="searchText" type="text" class="form-control" id="usr">
</div>
</div>
    <div id="searchButtonDiv" class="col-md-4"><span class="pull-right"><button id="searchButton" type="button" class="btn btn-info">Search</button>
</span></div>
  </div>
</div>

<div class="container">
<div id=appointmentTable> </div>
</div>

</body>
</html>