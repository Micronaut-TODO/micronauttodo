@param String csrfToken
<nav class="navbar border-bottom border-body">
    <div class="container-fluid">
        <a class="navbar-brand">Micronaut TODO</a>
        <form class="d-flex" action="/logout" method="post" data-turbo="false">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <button class="btn btn-primary" type="submit">Logout</button>
        </form>
    </div>
</nav>