# Revue de code: Tests unitaires de Booking.validate

Pour chaque test de `Booking.validate`, énumérez 3 points ne respectant pas les bonnes pratiques.

`givenBookingWithoutTraveler_whenValidate_thenThrowNoTravelerToBookException`:
- 1 Le test lance l'exception direct sur le mock avec doThrow, pas via un vrai appel à validate(). Si quelqu'un enlève la vérification travelers.isEmpty() dans Booking un jour, ce test va rester vert pareil.
- 2 Le setup monte un BookingService complet avec BookingFactory, TravelerFactory, les deux assemblers et LocalDateTimeParser juste pour obtenir une exception d'un mock. Un seul new Booking(id, List.of(), cabinType, dateTime) suivi d'un assertThrows donnerait le même résultat avec dix fois moins de code.
- 3 Le test passe par NewBookingDto et toute la chaîne de mapping. Change la forme du DTO et ce test casse, pour une raison qui a rien à voir avec la règle "pas de voyageur".

`givenDeparture5minutesBeforeBookingDateTime_whenValidate_thenThrowInvalidBookingDateException`:
- 1 Les deux LocalDateTime.now() sont appelés à des instants différents. L'écart de 5 minutes peut varier de quelques millisecondes selon la vitesse d'exécution de la machine, ce qui rend le test flaky. Une Clock fixe ou des dates codées en dur réglerait ça.
- 2 Le try/catch attrape seulement InvalidBookingDateException. Si validate() se met à lancer autre chose, l'exception remonte sans message clair au lieu d'un échec propre avec assertThrows qui dit exactement ce qui était attendu.
- 3 ANY_BOOKING_ID et ANY_DEPARTURE_DATE_TIME sont déjà définis en haut de la classe, mais ce test recrée ses propres valeurs ("id-123", 2012-03-03). Le jour où les constantes changent, ce test continue de rouler sur ses vieilles valeurs sans que personne s'en rende compte.

`whenValidate_thenDoNotThrow`:
- 1 La boucle teste deux Booking qui partagent exactement la même liste de travelers. Ça double le nombre d'assertions sans ajouter un seul cas réel, c'est le même scénario exécuté deux fois.
- 2 Les deux Booking reçoivent la même instance d'ArrayList en paramètre. Si un comportement futur modifie cette liste sur un Booking, l'autre Booking du test hérite du changement sans que ce soit voulu.
- 3 assertFalse(bookingTest.getTravelers().isEmpty()) teste un getter, pas validate(). Si cette ligne échoue un jour, ça va avoir l'air que validate() est cassé alors que le vrai problème sera ailleurs.
