<%@page session="false"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Global Kinetic | Register</title>

    <%@include file="init.jsp" %>

</head>

<div class="loading">Loading&#8230;
    <br/>
    <span id="loadingMessage"></span>
</div>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Kinetic &trade;</a>
        </div>     
    </div>
</nav>

<div class="container"> 
    <div class="starter-template center_div">  
           
            <form id="register-form" method="POST" action="/Kinetic/Register" data-toggle="validator" role="form">
                 
                <div class="form-group">
                    <label>Username</label>

                    <div class="input-group">
                        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required>
                    </div>
                    <!-- /.input group --> 
                </div>
                <div class="form-group">
                    <label>Phone</label>

                    <div class="input-group">
                        <input type="text" id="username" name="phone" class="form-control" placeholder="Phone" required>
                    </div>
                    <!-- /.input group --> 
                </div>
                <div class="form-group">
                    <label>Password</label>

                    <div class="input-group">
                        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                    </div>
                    <!-- /.input group --> 
                </div>
             
                <div class="form-group">   
                    <div class="input-group">
                        <button class="btn btn-success" type="submit">
                             Register
                        </button>
                    </div>
                    <br>
                    ${registrationMessage}
                    <!-- /.input group --> 
                    
                    
                    <div class="form-group">
                    <div class="input-group">
                        <button class="btn btn-success" type="submit">
                            <a class="navbar-brand" href="/Kinetic/login.jsp">Login</a>
                        </button>
                    </div>
               </div>
                </div>
            </form>    
        </div>
</div>

<link rel="stylesheet" type="text/css" href="static/css/jquery.dataTables.css">

<script type="text/javascript" src="static/js/jquery.dataTables.js"></script>

<!-- DataTables CSS -->
<link href="static/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="static/css/dataTables.responsive.css" rel="stylesheet">
<link href="static/css/app.css" rel="stylesheet">

<!-- DataTables JavaScript -->
<script src="static/js/jquery.dataTables.min.js"></script>
<script src="static/js/dataTables.bootstrap.min.js"></script>
<script src="static/js/dataTables.responsive.js"></script>

</body>
</html>