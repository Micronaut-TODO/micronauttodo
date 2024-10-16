@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.SelectFormElement
@param SelectFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
@if(el.hasErrors())
    <select name="${el.name()}"
            id="${el.id()}"
            class="form-select is-invalid"
            aria-describedby="${el.name()}ValidationServerFeedback"
            required="${el.required()}">
        @for(var option : el.options())
            @template.fieldset.option(el = option, messageSource = messageSource)
        @endfor
    </select>
    @template.fieldset.errors(name = el.name(), errors = el.errors(), messageSource = messageSource)
@else
    <select name="${el.name()}"
            id="${el.id()}"
            class="form-select"
            required="${el.required()}">
        @for(var option : el.options())
            @template.fieldset.option(el = option, messageSource = messageSource)
        @endfor
    </select>
@endif

