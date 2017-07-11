package quote

import static groovyx.net.http.ContentTypes.JSON
import static groovyx.net.http.HttpBuilder.configure

import groovyx.net.http.NativeHandlers.Parsers

class QuoteApiClient {

    private static final String ENDPOINT_URL = 'https://andruxnet-random-famous-quotes.p.mashape.com'
    private static final String API_KEY = 'YOUR_API_KEY'

    static Quote getMovieQuote() {
        return getRandomQuote('movies')
    }

    static Quote getFamousQuote() {
        return getRandomQuote('famouse')
    }

    private static Quote getRandomQuote(String category) {
        configure {
            request.uri = ENDPOINT_URL
            request.contentType = JSON[0]
            response.parser(JSON[0]) { config, resp ->
                def json = Parsers.json(config, resp)
                new Quote(json.quote, json.author)
            }
        }.get(Quote) {
            request.uri.path = '/'
            request.uri.query = [cat: category, count: 1]
            request.headers = [
                'X-Mashape-Key': API_KEY,
                'Accept'       : 'application/json'
            ]

            response.exception { t ->
                log.error "There was an error: $t"
            }
        }
    }
}
