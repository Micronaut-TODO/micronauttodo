@import io.micronaut.context.LocalizedMessageSource
@import java.util.List
@import io.micronaut.views.fields.messages.Message
@param String name
@param List<Message> errors
@param LocalizedMessageSource messageSource
@for(var error: errors)
<div id="${name}ValidationServerFeedback" class="invalid-feedback">${error.code() == null ? error.defaultMessage() : messageSource.getMessageOrDefault(error.code(), error.defaultMessage())}</div>
@endfor
