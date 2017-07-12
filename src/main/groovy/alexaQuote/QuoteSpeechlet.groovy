package alexaQuote

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.LaunchRequest
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SessionEndedRequest
import com.amazon.speech.speechlet.SessionStartedRequest
import com.amazon.speech.speechlet.Speechlet
import com.amazon.speech.speechlet.SpeechletException
import com.amazon.speech.speechlet.SpeechletResponse
import groovy.util.logging.Slf4j

@Slf4j
class QuoteSpeechlet implements Speechlet {

    @Override
    void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        log.info "onSessionStarted requestId=${request.requestId}, sessionId=${session.sessionId}"
    }

    @Override
    SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        log.info "onLaunch requestId=${request.requestId}, sessionId=${session.sessionId}"

        return QuoteIntents.helloMessage
    }

    @Override
    SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        log.info "onIntent requestId=${request.requestId}, sessionId=${session.sessionId}"

        Intent intent = request.getIntent()
        String intentName = intent?.name

        switch (intentName) {
            case 'GetMovieQuoteIntent': return QuoteIntents.movieQuote
            case 'GetFamousQuoteIntent': return QuoteIntents.famousQuote
            case 'AMAZON.HelpIntent': return QuoteIntents.helpResponse
            case 'AMAZON.StopIntent': return QuoteIntents.goodbye
            case 'AMAZON.CancelIntent': return QuoteIntents.goodbye

            default: throw new SpeechletException('Invalid Intent')
        }
    }

    @Override
    void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        log.info "onSessionEnded requestId=${request.requestId}, sessionId=${session.sessionId}"
    }
}
