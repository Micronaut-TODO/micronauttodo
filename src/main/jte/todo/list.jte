@import com.micronauttodo.domains.Todo
@import java.util.List
@param List<Todo> todos
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TODOs</title>
      <link rel="stylesheet" type="text/css" href="/assets/stylesheets/bootstrap-5.3.2/bootstrap.css">
      <script type="module">
          import * as Turbo from '/assets/javascripts/turbo8.0.4/turbo.min.js';
      </script>
      <script>
          window.onload = function() {
              document.getElementById('item').focus();
          };
      </script>
  </head>
  <body>
  <nav class="navbar bg-dark border-bottom border-body" data-bs-theme="dark">
      <div class="container-fluid">
          <a class="navbar-brand">Micronaut TODO</a>
          <form class="d-flex">
              <button class="btn btn-primary" type="submit">Logout</button>
          </form>
      </div>
  </nav>
  <div class="container">
  <ul id="todoList" class="list-group mt-3">
  @for(Todo todo : todos)
      @template.todo.todoListGroupItem(todo)
  @endfor
  </ul>
      <form action="/todo/save" method="post" class="mt-3">
          <div class="mb-3">
              <input type="text" name="item" class="form-control" id="item">
          </div>
          <button type="submit" class="btn btn-primary">Add</button>
      </form>
  </div>
  <script src="/assets/javascripts/bootstrap-5.3.2/bootstrap.bundle.min.js" />
  </body>
</html>