
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<html>

    <head>
        <title>Spring boot web application</title>  

        <link rel="stylesheet" type="text/css"  href="css/style.css">
        <link rel="stylesheet" type="text/css"  href="css/jquery.fancybox.css">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <script type="text/javascript" src="js/jquery.fancybox.js"></script>
        <script type="text/javascript" src="js/myscript.js"></script>

    </head>

    <body>

        <div >
            <p><a class="modalbox" href="#inline">Add New</a></p>
        </div>

        <!-- hidden inline form -->
        <div id="inline">
            <h2>Add</h2>



            <form id="contact" name="contact" action="#" method="post">
                <label >IBAN</label>
                <input type="text" id="iban" name="iban" size="32" class="txt"/> <h4 id="id1"></h4>
                <br>
                <label >Business Identifier Code</label>
                <input type="text" name="bic" id="bic" size="32" class="txt"/>   <h4 id="id2"></h4>

                <button id="send">Save</button>

            </form>
        </div>


        <table id="accountTable">
                <tr>
                    <th></th>
                    <th></th>
                    <th>IBAN</th>
                    <th>Business Identifier Code</th>		
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${accountList}" var="account">


                    <tr>
                        <td><input type="hidden" value="${account.version}" id="ver${account.id}"></td>
                        <td><input type="hidden" value="${account.id}"  size="6"/></td>
                        <td><input type="text" value="${account.iban}" disabled id="iban${account.id}" size="32"></td>
                        <td><input type="text" value="${account.bic}" disabled id="bic${account.id}" size="32"></td>
                        <c:if test="${account.id > 5}"> <!-- above 5 account ids are user values so i am enable edit -->
                            <td><a href="#" onclick="editForm(${account.id})" >Edit</a> </td>
                            <td><a href="#" onclick="deleteRow(this,${account.id})" >Delete</a> </td>
                            <td><input type="button" value="Save" disabled id="button${account.id}" onclick="saveForm(${account.id})"/></td>
                            <td><span id="error${account.id}"></span></td>
                            </c:if>
                    </tr>
                </c:forEach>
            </table>

    </body>

</html>