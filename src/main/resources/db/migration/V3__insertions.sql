-- -----------------------------------------------------
-- Table klienci
-- -----------------------------------------------------
INSERT INTO osoby (id, imie, nazwisko, numer_dowodu_osobistego, numer_telefonu)
VALUES ('1', 'Pawel', 'Stateczny', 'AWK585987', '566687451');
INSERT INTO osoby (id, imie, nazwisko, numer_dowodu_osobistego, numer_telefonu)
VALUES ('2', 'Kacper', 'Kus', 'AWP743246', '833445551');


-- -----------------------------------------------------
-- Table pojazdy
-- -----------------------------------------------------

INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('1', 'Honda', 'Integra TYPE R', '207', '12', '150',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/integra.jpg?alt=media&token=33c170ce-571f-4453-aaf8-ea0b54a0c594');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('2', 'Renault', 'Clio', '110', '8', '90',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/cllio.jpg?alt=media&token=340c92bd-70f1-4c2a-9780-b94a53ea6230');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('3', 'Ford', 'Mustang', '375', '15', '280',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/Ford-Mustang.jpg?alt=media&token=b7f110a4-b9dc-4891-bce9-98aac396fd47');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('4', 'Ford', 'Focus RS', '350', '16', '250',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/ford-focus.jpg?alt=media&token=75766c07-2714-438b-9ec5-1ac00ebd3492');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('5', 'Ford', 'Galaxy', '150', '7', '120',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/ford-galaxy.jpg?alt=media&token=ab16375d-68a4-4795-a8b5-fb213f24a235');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('6', 'Fiat', 'Multipla', '170', '10', '110',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/multipla.jpg?alt=media&token=d02455fd-5a71-4358-a1f2-a1470221b08a');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('7', 'Mercedes', 'C220', '170', '9', '130',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/mercedes.jpg?alt=media&token=eaf5423f-6987-40b5-b3eb-655a2020002c');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('8', 'Renault', 'Twizy', '7', '=|=', '70',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/twizy.jpg?alt=media&token=a24c4b82-5100-431b-b17e-9bfd36efbae6');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('9', 'Seat', 'Leon cupra', '225', '12', '150',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/leon.jpg?alt=media&token=0a4d42ac-e8e5-4f14-9e6b-76b80e131145');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('10', 'Audi', 'A4', '220', '11', '200',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/audi.jpg?alt=media&token=a4763abb-8968-4c4d-b959-fb6846851df5');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('11', 'Toyota', 'AE 86 trueno', '130', '10', '400',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/toyota.jpg?alt=media&token=8a6687b5-fec9-4680-8ffe-c4ad015f1b3d');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('12', 'Volkswagen', 'Transporter', '150', '9', '170',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/volkswagen-transporter.jpg?alt=media&token=37c509f3-8598-44f1-ace7-b9cb9cab39ea');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('13', 'Mazda', 'RX8', '231', '17', '185',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/mazda.jpg?alt=media&token=b006678d-1923-4322-a743-cfd8b6813641');
INSERT INTO pojazdy (id, marka, model, moc, srednie_spalanie, cena_za_dobe, miniaturka)
VALUES ('14', 'Honda', 'CRX', '150', '11', '150',
        'https://firebasestorage.googleapis.com/v0/b/fast-car-app.appspot.com/o/hondacrz.jpeg?alt=media&token=da1485b8-6727-4c6c-a7a2-de632d290ad5');


-- -----------------------------------------------------
-- Table wynajmy
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table zobowiazania
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table konta
-- -----------------------------------------------------
INSERT INTO konta (id, id_osoby, dane_logowania, rola)
VALUES ('1', '1', 'cGF3ZWw6c3RhdGVjem55', 'BOSS');
INSERT INTO konta (id, id_osoby, dane_logowania, rola)
VALUES ('2', '2', 'a2FjcGVyOmt1cw==', 'CLIENT');
