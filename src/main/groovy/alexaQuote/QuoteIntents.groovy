package alexaQuote

import com.amazon.speech.speechlet.SpeechletResponse
import com.amazon.speech.ui.PlainTextOutputSpeech
import com.amazon.speech.ui.Reprompt
import com.amazon.speech.ui.SimpleCard
import groovy.transform.CompileStatic
import quote.Quote
import quote.QuoteApiClient
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class QuoteIntents {

     static SpeechletResponse getHelpResponse() {
        String speechText = 'You can ask Quoter for me a movie or a famous quote, or, you can say exit. How can I help you?'

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech(text: speechText)
        Reprompt reprompt = new Reprompt(outputSpeech: speech)

        return SpeechletResponse.newAskResponse(speech, reprompt)
    }

    static SpeechletResponse getGoodbye() {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech(text: 'Goodbye')

        return SpeechletResponse.newTellResponse(outputSpeech)
    }

    static SpeechletResponse getHelloMessage() {
        // Create speech output
        String speechText = 'Hello, welcome to Quoter. You can ask me for a new movie or a famous quote.'
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech(text: speechText)

        // Create reprompt
        Reprompt reprompt = new Reprompt(outputSpeech: speech)

        // Create the Simple card content
        SimpleCard card = new SimpleCard(
            title: 'Quoter',
            content: speechText
        )

        return SpeechletResponse.newAskResponse(speech, reprompt, card)
    }

    static SpeechletResponse getMovieQuote() {
        Quote quote = QuoteApiClient.movieQuote
        
        log.debug "quote -> ${quote}"

        String speechText = quote ?
            "Here is a quote from the movie ${quote.author}: ${quote.text}" :
            'Ummm. It seems that there is a problem with the Quotes. Please try again later.'

        // Create the Simple card content
        SimpleCard card = new SimpleCard(
            title: 'Quoter',
            content: speechText
        )

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech(text: speechText)

        return SpeechletResponse.newTellResponse(speech, card)
    }

    static SpeechletResponse getFamousQuote() {
        Quote quote = QuoteApiClient.famousQuote

        log.debug "quote -> ${quote}"

        String speechText = quote ?
            "Here is a quote from ${quote.author}: ${quote.text}" :
            'Ummm. It seems that there is a problem with the Quotes. Please try again later'

        // Create the Simple card content
        SimpleCard card = new SimpleCard(
            title: 'Quoter',
            content: speechText
        )

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech(text: speechText)

        return SpeechletResponse.newTellResponse(speech, card)
    }
}
