@import io.micronaut.views.fields.Form
@param Form localeForm
@param String csrfToken
<nav class="navbar bg-dark border-bottom border-body" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand">Micronaut TODO</a>
        <form class="d-flex" action="/logout" method="post" data-turbo="false">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <button class="btn btn-primary" type="submit">Logout</button>
        </form>
    </div>
</nav>