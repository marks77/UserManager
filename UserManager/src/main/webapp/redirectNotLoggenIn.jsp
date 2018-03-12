<c:if test="${empty token}">
    <c:redirect url="login.jsp"/>
</c:if>