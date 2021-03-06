package be.vdab.restclients;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//@Component
//@Qualifier("Fixer")
class FixerKoersenClient implements KoersenClient {
	private static final Logger LOGGER = Logger.getLogger(FixerKoersenClient.class.getName());
	private final URL url;

	FixerKoersenClient(URL url) { //@Value("${fixerKoersenURL}") 
		this.url = url;
	}

	@Override
	public BigDecimal getDollarKoers() {
		try (Scanner scanner = new Scanner(url.openStream())) {
			String lijn = scanner.nextLine();
			int beginPositieKoers = lijn.indexOf("USD") + 5;
			int accoladePositie = lijn.indexOf("}", beginPositieKoers);
			System.out.println("met fixer");
			return new BigDecimal(lijn.substring(beginPositieKoers, accoladePositie));
		} catch (IOException | NumberFormatException ex) {
			LOGGER.log(Level.SEVERE, "kan koers niet lezen via Fixer", ex);
			return null;
		}
	}
}