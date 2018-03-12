<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Global Kinetic | Home</title>

        <%@include file="init.jsp" %>

    </head>
    <body>
        <%@include file="redirectNotLoggenIn.jsp" %> 
        
        <div class="loading">Loading&#8230;
            <br/>
            <span id="loadingMessage"></span>
        </div>
        
        <nav class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">Global Kinetic &trade;</a> 
                    <a class="navbar-brand" href="/Kinetic/Logout">Log out</a>
                </div>    
                
            </div>
        </nav>

        <div class="container"> 
            <div class="starter-template"> 
                <div id="tablesDiv">       
                    <form id="displayForm" method="POST" action="/Kinetic/Users">
                        <div class="form-group">
                            <div class="input-group">
                            <label>Users:</label>

                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-apple"></i>
                                </div>
                                <select class="form-control validate[required]" style="width: 100%;" id="loginOption" name="loginOption">
                                    <option value="0">---Please select an option---</option>
                                    <option value="1">All registered users</option>
                                    <option value="2">Authenticated users</option>
                                    <option value="3">Logged in the last 5 minutes</option>
                                </select>
                            </div>
                            
                            <div class="form-group">   
                                <div class="input-group">
                                    <br>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn btn-success" type="submit">
                                        Get Results
                                   </button>
                                </div>
                            <!-- /.input group --> 
                            </div>
                            </div> 
                        </div>
                    </form>   
                    
                    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
                            <tr>
                                <td align="center">ID</td>
                                <td align="center">Phone</td>
                            </tr>

                            <c:forEach items="${userData}" var="stList">
                                <tr>
                                    <td>${stList.id}</td>
                                    <td>${stList.phone}</td>
                                </tr>
                            </c:forEach>

                    </table>
                </div>
            </div>
        </div>

        <link rel="stylesheet" type="text/css" href="static/css/jquery.dataTables.css">

        <script type="text/javascript" src="static/js/jquery.dataTables.js"></script>

        <!-- DataTables CSS -->
        <link href="static/css/dataTables.bootstrap.css" rel="stylesheet">
        <link href="static/css/dataTables.responsive.css" rel="stylesheet">

        <!-- DataTables JavaScript -->
        <script src="static/js/jquery.dataTables.min.js"></script>
        <script src="static/js/dataTables.bootstrap.min.js"></script>
        <script src="static/js/dataTables.responsive.js"></script>
    </body>
</html>