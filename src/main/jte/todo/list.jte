@import io.micronaut.context.LocalizedMessageSource
@import com.micronauttodo.domains.Todo
@import java.util.List
@import io.micronaut.views.fields.Form
@param List<Todo> todos
@param Form localeForm
@param String csrfToken
@param LocalizedMessageSource messageSource
<!DOCTYPE html>
<html lang="en">
  <head>
      <meta name="csrf-token" content="${csrfToken}">
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>TODOs</title>
      <link rel="stylesheet" type="text/css" href="/assets/stylesheets/bootstrap-5.3.3-dist/bootstrap.css">
      <script type="module">
          import * as Turbo from '/assets/javascripts/turbo8.0.4/turbo.min.js';
      </script>
      <script>
          window.onload = function() {
              document.getElementById('item').focus();
          };
      </script>
      <script type="module">
          import {Application, Controller} from "/assets/javascripts/stimulus-3.2.1.js"

          window.Stimulus = Application.start()

          Stimulus.register("reset-form", class extends Controller {
              reset() {
                  this.element.reset()
              }
          })
      </script>
  </head>
  <body>
  @template.nav(localeForm = localeForm, csrfToken = csrfToken)
  <div class="container">
  <ul id="todoList" class="list-group mt-3">
  @for(Todo todo : todos)
      @template.todo.todoListGroupItem(todo)
  @endfor
  </ul>
      <form action="/todo/save" method="post" class="mt-3" data-controller="reset-form" data-action="turbo:submit-end->reset-form#reset">
          <div class="mb-3">
              <input type="text" name="item" class="form-control" id="item">
          </div>
          <button type="submit" class="btn btn-primary">${messageSource != null ? messageSource.getMessageOrDefault("default.button.create.label", "Add") : "Add"}</button>
      </form>
  </div>
  <script type="text/javascript" src="/assets/javascripts/app.js"></script>
  <script type="text/javascript" src="/assets/javascripts/bootstrap-5.3.3-dist/bootstrap.bundle.min.js" />
  </body>
</html>