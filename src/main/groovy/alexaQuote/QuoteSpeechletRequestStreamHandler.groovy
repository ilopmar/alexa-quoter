package alexaQuote

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler

class QuoteSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
//         supportedApplicationIds.add("xxxxxxxxxxxxxxxxxxxxxxx");

    }

    QuoteSpeechletRequestStreamHandler() {
        super(new QuoteSpeechlet(), supportedApplicationIds);
    }
}
