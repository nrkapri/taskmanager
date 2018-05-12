<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My Tasks</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	
	<script type="text/javascript">
// Load google charts
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

// Draw the chart and set the chart values
function drawChart() {
  var data = google.visualization.arrayToDataTable([
  ['Task Status', 'Count'],
  ['Pending', ${pendingTasks }],
  ['Done', ${doneTasks }],
  ['Cancelled', ${deletedTasks }]
]);

  // Optional; add a title and set the width and height of the chart
  var options = {'title':'My Tasks Pie',
		   		 
		   		 'colors':['red','green','grey'] };

  // Display the chart inside the <div> element with id="piechart"
  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  chart.draw(data, options);
}
</script>
</head>
<body>
	<div class="container">
		<div class="card bg-primary text-white">
			<div class="card-body">
				<p class="font-weight-bold">${message }</p>
			</div>
		</div>
		<div class="card">
			<div class="card-body">
			 <div class="row">
    <div class="col-sm-4" "><p class="font-weight-bold">Emp Id: ${user.eid}<br /> Email:${user.email}</p></div>
    <div class="col-sm-8" "><div id="piechart"></div></div>
  </div>
				
			</div>
		</div>

		<c:if test="${not empty user.tasks}">
			<c:set var="flag" value="0" />
			<c:forEach var="task" items="${user.tasks}">
				<c:choose>
					<c:when test="${flag == 0}">
						<div class="card bg-info text-dark">
							<c:set var="flag" value="1" />
					</c:when>
					<c:otherwise>
						<div class="card bg-secondary text-dark">
							<c:set var="flag" value="0" />
					</c:otherwise>
				</c:choose>
				<!-- 				<div class="card-header"> -->
				<div class="card-body">
					<p class="font-weight-bold">
						Created at:${task.taskCreationTime}
						<c:if test="${task.taskStatus == 'C'}">
							<span class="badge badge-success">Done</span>
						</c:if>
						<c:if test="${ task.taskStatus == 'D'}">
							<span class="badge badge-secondary">Deleted</span>
						</c:if>
						<c:if test="${task.taskStatus != 'C' && task.taskStatus != 'D'}">
							<span class="badge badge-danger">Pending</span>
						</c:if>
					</p>
					<!-- 				</div> -->
					<!-- 				<div class="card-body"> -->
					<p class="font-weight-bold">${task.taskDesc}
						<c:if test="${task.taskStatus!='C' && task.taskStatus!='D'}">
							<div>
							<a href="updateTaskStatus?taskStatus=C&taskDesc=${task.taskDesc}" class="btn btn-success btn-xs" role="button">Mark Done</a>
							<a href="updateTaskStatus?taskStatus=D&taskDesc=${task.taskDesc}" class="btn btn-danger btn-xs" role="button">Delete</a>
							</div>
						</c:if>
					</p>
				</div>
	</div>
	</c:forEach>
	</c:if>

	<form:form class="form-inline" method="POST"
		action="/addTask?email=${user.email}&password=${user.password}"
		modelAttribute="task">
		<div class="form-group">
			<label class="sr-only" for="taskDesc">Task Desc:</label>
			<form:input type="text" class="form-control" path="taskDesc"
				placeholder="Enter task description" />
		</div>
		<button type="submit" class="btn btn-default">Add new Task</button>
	</form:form>

	</div>


</body>
</html>