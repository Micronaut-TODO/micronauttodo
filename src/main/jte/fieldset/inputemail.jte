@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputEmailFormElement
@import io.micronaut.views.fields.elements.InputStringFormElement
@param InputEmailFormElement el
@param LocalizedMessageSource messageSource
@if(el instanceof InputStringFormElement inputStringFormElement)
@template.fieldset.inputstring(type = "email", el = inputStringFormElement, messageSource = messageSource)
@endif

