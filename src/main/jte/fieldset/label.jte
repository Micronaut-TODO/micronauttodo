@import io.micronaut.views.fields.messages.Message
@import io.micronaut.context.LocalizedMessageSource
@param LocalizedMessageSource messageSource
@param String id
@param Message el
<label for="${id != null ? id : null}" class="form-label">${el.code() == null ? el.defaultMessage() : messageSource.getMessageOrDefault(el.code(), el.defaultMessage())}</label>
