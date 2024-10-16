@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputDateFormElement
@param InputDateFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
@if(el.hasErrors())
<input
    type="date"
    name="${el.name()}"
    value="${el.value() != null ? el.value().toString() : null}"
    id="${el.id()}"
    min="${el.min() != null ? el.min().toString() : null}"
    max="${el.max() != null ? el.max().toString() : null}"
    step="${el.step()}"
    class="form-control is-invalid"
    aria-describedby="${el.name()}ValidationServerFeedback"
    required="${el.required()}"
    />
    @template.fieldset.errors(name = el.name(), errors = el.errors(), messageSource = messageSource)
@else
<input
    type="date"
    name="${el.name()}"
    value="${el.value() != null ? el.value().toString() : null}"
    id="${el.id()}"
    min="${el.min() != null ? el.min().toString() : null}"
    max="${el.max() != null ? el.max().toString() : null}"
    step="${el.step()}"
    class="form-control"
    required="${el.required()}"/>
@endif
