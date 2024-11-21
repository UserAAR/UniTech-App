package az.edu.turing.mstransfer.util;

import az.edu.turing.mstransfer.exceptions.NotFoundException;
import az.edu.turing.mstransfer.model.enums.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static az.edu.turing.mstransfer.model.enums.Error.ERR_03;

@Service
@RequiredArgsConstructor
public class CurrencyRateFetcher {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String REDIS_KEY_PREFIX = "currency:";
    private static final int CACHE_EXPIRATION_MINUTES = 1;

    private final RedisTemplate<String, String> redisTemplate;

    private static String getCurrencyRatesUrl() {
        LocalDate currentDate = LocalDate.now();
        return "https://cbar.az/currencies/" + currentDate.format(formatter) + ".xml";
    }

    public void fetchExchangeRates() throws Exception {
        String currencyRatesUrl = getCurrencyRatesUrl();
        URL url = new URL(currencyRatesUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to fetch data: HTTP error code : " + connection.getResponseCode());
        }

        InputStream xmlInput = connection.getInputStream();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlInput);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("Valute");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String code = element.getAttribute("Code");
                BigDecimal value = new BigDecimal(element.getElementsByTagName("Value").item(0).getTextContent());


                if ("RUB".equals(code)) {
                    value = value.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
                }


                redisTemplate.opsForValue().set(
                        REDIS_KEY_PREFIX + code,
                        value.toPlainString(),
                        CACHE_EXPIRATION_MINUTES,
                        TimeUnit.MINUTES
                );
            }
        }

        connection.disconnect();
    }

    public BigDecimal getRate(Currency currency) {
        if (currency == Currency.AZN) {
            return BigDecimal.ONE;
        }

        String rateString = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + currency.name());
        if (rateString == null) {
            throw new NotFoundException(ERR_03.getErrorCode(), ERR_03.getErrorDescription() + currency.name());
        }

        return new BigDecimal(rateString);
    }
}
