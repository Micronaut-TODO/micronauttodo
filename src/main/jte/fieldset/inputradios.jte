@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputRadioFormElement
@param InputRadioFormElement el
@param LocalizedMessageSource messageSource
@for(var radio : el.buttons())
    <div class="form-check">
        @template.fieldset.inputradio(el, radio)
        @template.fieldset.label(id = radio.id(), el = radio.label(), messageSource = messageSource)
    </div>
@endfor
