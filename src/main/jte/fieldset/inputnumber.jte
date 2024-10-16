@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputNumberFormElement
@param InputNumberFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
@if(el.hasErrors())
<input type="number"
       name="${el.name()}"
       value="${el.value()}"
       id="${el.id()}"
       min="${el.min()}"
       max="${el.max()}"
       step="${el.step()}"
       placeholder="${el.placeholder()}"
       readOnly="${el.readOnly()}"
       class="form-control is-invalid"
        aria-describedby="${el.name()}ValidationServerFeedback"
       required="${el.required()}"/>
    @template.fieldset.errors(name = el.name(), errors = el.errors(), messageSource = messageSource)
@else
<input type="number"
       name="${el.name()}"
       value="${el.value()}"
       id="${el.id()}"
       min="${el.min()}"
       max="${el.max()}"
       step="${el.step()}"
       placeholder="${el.placeholder()}"
       readOnly="${el.readOnly()}"
       class="form-control"
       required="${el.required()}"/>
@endif

