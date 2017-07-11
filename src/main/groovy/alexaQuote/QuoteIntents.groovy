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

    private static final String ERROR_MSG = 'Ummm... It seems that there is a problem with the Quotes. ' +
                                            'Please try again later.'

    static SpeechletResponse getHelpResponse() {
        String speechText = 'You can ask Quoter for me a movie or a famous quote. How can I help you?'

        return buildAskResponse(speechText)
    }

    static SpeechletResponse getGoodbye() {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech(text: 'Goodbye')

        return SpeechletResponse.newTellResponse(outputSpeech)
    }

    static SpeechletResponse getHelloMessage() {
        String speechText = 'Hello, welcome to Quoter. You can ask me for a new movie or a famous quote.'

        return buildAskResponse(speechText)
    }

    static SpeechletResponse getMovieQuote() {
        Quote quote = QuoteApiClient.movieQuote
        
        log.debug "quote -> ${quote}"

        String speechText = quote ?
            "Here is a quote from the movie ${quote.author}: ${quote.text}" : ERROR_MSG

        return buildTellResponse(speechText)
    }

    static SpeechletResponse getFamousQuote() {
        Quote quote = QuoteApiClient.famousQuote

        log.debug "quote -> ${quote}"

        String speechText = quote ?
            "Here is a quote from ${quote.author}: ${quote.text}" : ERROR_MSG

        return buildTellResponse(speechText)
    }

    private static SimpleCard buildCard(String speechText) {
        return new SimpleCard(
            title: 'Quotes from Movies and Famous',
            content: speechText
        )
    }

    private static SpeechletResponse buildTellResponse(String speechText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech(text: speechText)
        SimpleCard card = buildCard(speechText)

        return SpeechletResponse.newTellResponse(speech, card)
    }

    private static SpeechletResponse buildAskResponse(String speechText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech(text: speechText)
        Reprompt reprompt = new Reprompt(outputSpeech: speech)
        SimpleCard card = buildCard(speechText)

        return SpeechletResponse.newAskResponse(speech, reprompt, card)
    }
}
