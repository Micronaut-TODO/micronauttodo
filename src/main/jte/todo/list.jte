@import io.micronaut.context.LocalizedMessageSource
@import com.micronauttodo.domains.Todo
@import java.util.List
@import io.micronaut.views.fields.Form
@param List<Todo> todos
@param Form localeForm
@param String csrfToken
@param LocalizedMessageSource messageSource
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
    <title>${messageSource.getMessageOrDefault("todo.title", "Todos")}</title>
    @template.htmlHead(csrfToken = csrfToken)
    <script>
        window.onload = function() {
            document.getElementById('item').focus();
        };
    </script>
</head>
<body class="bg-body-tertiary">
@template.appearance()
@template.locale(localeForm = localeForm, messageSource = messageSource)

@template.nav(csrfToken = csrfToken)
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

@template.htmlBodyScript()
</body>
</html>