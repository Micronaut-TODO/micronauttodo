@import io.micronaut.views.fields.Fieldset
@import io.micronaut.views.fields.elements.InputCheckboxFormElement
@import io.micronaut.views.fields.elements.InputHiddenFormElement
@import io.micronaut.views.fields.elements.InputNumberFormElement
@import io.micronaut.views.fields.elements.InputEmailFormElement
@import io.micronaut.views.fields.elements.InputTelFormElement
@import io.micronaut.views.fields.elements.InputTextFormElement
@import io.micronaut.views.fields.elements.InputUrlFormElement
@import io.micronaut.views.fields.elements.SelectFormElement
@import io.micronaut.views.fields.elements.InputSubmitFormElement
@import io.micronaut.views.fields.elements.InputPasswordFormElement
@import io.micronaut.views.fields.elements.InputDateFormElement
@import io.micronaut.views.fields.elements.InputDateTimeLocalFormElement
@import io.micronaut.views.fields.elements.TextareaFormElement
@import io.micronaut.views.fields.elements.TrixEditorFormElement
@import io.micronaut.views.fields.elements.InputTimeFormElement
@import io.micronaut.views.fields.elements.InputStringFormElement
@import io.micronaut.views.fields.elements.InputFileFormElement
@import io.micronaut.context.LocalizedMessageSource
@param Fieldset el
@param LocalizedMessageSource messageSource
@for(var field : el.fields())
    @if(field instanceof InputHiddenFormElement inputHiddenFormElement)
            @template.fieldset.inputhidden(inputHiddenFormElement)
    @endif
    @if(field instanceof InputCheckboxFormElement ||
        field instanceof SelectFormElement ||
        field instanceof TextareaFormElement ||
        field instanceof TrixEditorFormElement ||
        field instanceof InputTimeFormElement ||
        field instanceof InputDateFormElement ||
        field instanceof InputDateTimeLocalFormElement ||
        field instanceof InputStringFormElement ||
        field instanceof InputNumberFormElement)
        <div class="mb-3">
            @if(field instanceof InputCheckboxFormElement inputCheckboxFormElement)
                @template.fieldset.inputcheckbox(el = inputCheckboxFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof SelectFormElement selectFormElement)
                @template.fieldset.select(el = selectFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof TextareaFormElement textareaFormElement)
                @template.fieldset.textarea(el = textareaFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof TrixEditorFormElement trixEditorFormElement)
                @template.fieldset.trixeditor(el = trixEditorFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputTimeFormElement inputTimeFormElement)
                @template.fieldset.inputtime(el = inputTimeFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputDateTimeLocalFormElement inputDateTimeLocalFormElement)
                @template.fieldset.inputdatetimelocal(el = inputDateTimeLocalFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputPasswordFormElement inputPasswordFormElement)
                @template.fieldset.inputpassword(el = inputPasswordFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputTextFormElement inputTextFormElement)
                @template.fieldset.inputtext(el = inputTextFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputNumberFormElement inputNumberFormElement)
                @template.fieldset.inputnumber(el = inputNumberFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputEmailFormElement inputEmailFormElement)
                @template.fieldset.inputemail(el = inputEmailFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputTelFormElement inputTelFormElement)
                @template.fieldset.inputtel(el = inputTelFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputUrlFormElement inputUrlFormElement)
                @template.fieldset.inputurl(el = inputUrlFormElement, messageSource = messageSource)
            @endif
            @if(field instanceof InputDateFormElement inputDateFormElement)
                @template.fieldset.inputdate(el = inputDateFormElement, messageSource = messageSource)
            @endif
        </div>
    @endif
    @if(field instanceof InputFileFormElement inputFileFormElement)
        @template.fieldset.inputfile(el = inputFileFormElement, messageSource = messageSource)
    @endif
    @if(field instanceof InputSubmitFormElement inputSubmitFormElement)
        @template.fieldset.inputsubmit(el = inputSubmitFormElement, messageSource = messageSource)
    @endif

@endfor
@if(el.hasErrors())
    @for(var error: el.errors())
        <div class="alert alert-danger mt-2" role="alert">${error.code() == null ? error.defaultMessage() : messageSource.getMessageOrDefault(error.code(), error.defaultMessage())}</div>
    @endfor
@endif