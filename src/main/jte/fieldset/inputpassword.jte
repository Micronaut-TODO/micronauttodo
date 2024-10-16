@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputPasswordFormElement
@import io.micronaut.views.fields.elements.InputStringFormElement
@param InputPasswordFormElement el
@param LocalizedMessageSource messageSource
@if(el instanceof InputStringFormElement inputStringFormElement)
@template.fieldset.inputstring(type = "password", el = inputStringFormElement, messageSource = messageSource)
@endif

