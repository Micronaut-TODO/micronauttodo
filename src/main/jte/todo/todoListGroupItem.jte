@import com.micronauttodo.domains.Todo
@param Todo todo
<li class="list-group-item" id="todo${todo.id()}">
    <form action="/todo/delete" method="post" class="float-end">
        <input type="hidden" name="id" value="${todo.id()}"/>
        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
    </form>
    <span class="align-middle">${todo.item()}</span>
</li>