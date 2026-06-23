<%@ include file="/init.jsp" %>

<%-- Create a URL pointing back to the default view.jsp --%>
<portlet:renderURL var="backToHomeURL">
    <portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<div class="container-fluid">
    <div class="sheet">
        <h1 class="sheet-title">Welcome to Tab 2 / Details Page!</h1>
        <p class="sheet-text">You successfully routed here without reloading the whole Liferay website.</p>

        <a href="${backToHomeURL}" class="btn btn-secondary">Back to Main Tab V2</a>
    </div>
</div>
