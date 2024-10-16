@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.TrixEditorFormElement
@param TrixEditorFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
<input type="hidden" name="${el.name()}" value="${el.value()}" id="${el.id()}"/>
<trix-editor input="${el.id()}"></trix-editor>
