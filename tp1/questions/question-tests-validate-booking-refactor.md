# Revue de code: Tests unitaires de Booking.validate

Pour chaque test de `Booking.validate`, énumérez 3 points ne respectant pas les bonnes pratiques.

`givenBookingWithoutTraveler_whenValidate_thenThrowNoTravelerToBookException`:
- 1 Le test lance l'exception direct sur le mock avec doThrow, pas via un vrai appel à validate(). Si quelqu'un enlève la vérification travelers.isEmpty() dans Booking un jour, ce test va rester vert pareil.
- 2 Le setup monte un BookingService complet avec BookingFactory, TravelerFactory, les deux assemblers et LocalDateTimeParser juste pour obtenir une exception d'un mock. Un seul new Booking(id, List.of(), cabinType, dateTime) suivi d'un assertThrows donnerait le même résultat avec dix fois moins de code.
- 3 Le test passe par NewBookingDto et toute la chaîne de mapping. Change la forme du DTO et ce test casse, pour une raison qui a rien à voir avec la règle "pas de voyageur".

`givenDeparture5minutesBeforeBookingDateTime_whenValidate_thenThrowInvalidBookingDateException`:
- 1 Les dates sortent de LocalDateTime.now(), donc elles changent à chaque exécution. Un échec vu une fois ne peut pas être rejoué avec les mêmes valeurs. Des dates fixes règlent ça, il y en a déjà en constantes en haut de la classe.
- 2 Un try/catch avec un booléen fait le travail de assertThrows. Si validate() ne lance rien, le message d'échec dit juste qu'on attendait true, sans nommer l'exception manquante.
- 3 Les variables s'appellent t1, t, cdt, b et dep. Il faut relire le constructeur de Booking pour comprendre laquelle des deux dates est celle de la réservation et laquelle est le départ.

`whenValidate_thenDoNotThrow`:
- 1 La boucle passe deux Booking identiques à part leur id, et l'id ne joue aucun rôle dans validate(). C'est le même scénario exécuté deux fois, et si un des deux échoue le rapport ne dit pas lequel.
- 2 Les deux Booking reçoivent la même instance d'ArrayList en paramètre. Si un comportement futur modifie cette liste sur un Booking, l'autre Booking du test hérite du changement sans que ce soit voulu.
- 3 assertFalse(bookingTest.getTravelers().isEmpty()) teste un getter, pas validate(). Si cette ligne échoue un jour, ça va avoir l'air que validate() est cassé alors que le vrai problème sera ailleurs.
