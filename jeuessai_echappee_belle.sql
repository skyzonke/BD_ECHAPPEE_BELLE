insert into ROLE(idro,nomrole) values (1,'Admin'),(2,'Joueur'),(3,'Concepteur');

insert into TYPETEXTE (idtt, nomtt) values (1,'Introduction'),(2,'Victoire'),(3,'Echec');

insert into UTILISATEUR(idut, pseudout, emailut, mdput, activeut, idro) values
       (1,'admin','admin@echappee-belle.fr',password('mdp1'),'O',1),
       (2,'iuto','iuto@univ.fr',password('mdpiuto'),'O',2),
       (3,'iutc','iutc@univ.fr',password('mdpiutc'),'O',2),
       (4,'iutv','iutv@univ.fr',password('mdpiutv'),'O',2),
       (5,'concept1','concept1@echappee-belle.fr',password('mdpc1'),'O',3),
       (6,'concept2','concept2@echappee-belle.fr',password('mdpc2'),'O',3) ;

insert into ENIGME(iden, nomen, texten, reponseen, aideen, brouillonen, idut)  values
       (1,'L''addition','Quel nombre se cache derrière cette addition?','618','Pensez romain mais écrivez arabe!','N',5),
       (2,'Les carrés','Combien de carrés comporte cette figure?','14','Certains carrés comportent d''autres carrés','N',5),
       (3,'Etudes','Quel est le meilleur département de l''IUT''O?','INFORMATIQUE','Son nom n''est pas un acronyme','N',6),
       (4,'Famille','Les parents de Marie ont trois enfants: Quentin, Julien et ?','MARIE','Ce n''est pas forcement un gars','N',5),
       (5,'Cadenas','Pour déverouiller la porte, il faut pianoter sur le téléphone un code qui est la somme de toutes les énigmes','632','Le code comporte 3 chiffres','N',5),
       (6,'L''épée','Pour sortir de cette forêt maléfique il faut nommer le propriétaire de cette épée!\nLes lettres de se prénom se trouve dans un département de l''IUT''O!','QUENTIN','Il s''agit d''un des frères de Marie','N',5);

insert into SCENARIO (idsc , titresc, resumesc, tpsmaxsc, datemiseenligne,  brouillonsc , idut) values
       (1,'Déconfinez moi!','Saurez vous sortir de chez vous pour changer d''air?',
        '0:10', STR_TO_DATE('20/04/2021','%d/%m/%Y'),'N',5),
       (2,'Le Donjon enchanté','Battez les créatures mystérieuses et sortez du donjon',
        '0:10', NULL,'O',6);

insert into TILESET(idts, nomts,tailletuile) values (1,'Forêt',32), (2,'Atelier',48),(3,'Maison',32);

insert into CARTE(idCa,nomCa,planCa,brouillonCa,idut,idts) values
       (1,'Maison','{"plan": [[7, 3, 3, 3, 3, 3, 3, 3, 3, 8],[19, 26, 40, 41, 24, 13, 13, 23, 24, 19],[19, 36, 50, 51, 34, 2, 2, 2, 34, 19], [19, 2, 2, 2, 2, 2, 2, 2, 25, 19], [19, 57, 58, 44, 45, 46, 2, 2, 35, 19], [27, 3, 6, 54, 55, 56, 5, 3, 3, 38], [19, 60, 13, 16, 47, 48, 15, 13, 61, 19], [19, 70, 1, 33, 1, 1, 1, 1, 71, 19], [19, 1, 1, 1, 1, 1, 1, 1, 49, 19], [17, 9, 9, 9, 9, 9, 9, 9, 9, 18]]}','N',5,3),
       (2,'Forêt','{"plan": [[101,101,101,118,119,46,47,112,112,112],[116,117,112,136,137,64,65,116,117,112],[134,135,112,154,155,79,80,134,135,112],[152,153,112,172,173,112,112,152,153,112],[170,171,114,112,112,112,112,170,171,98],[112,112,112,112,115,112,77,72,73,74],[41,99,112,112,168,77,37,90,91,92],[19,22,78,112,77,37,37,108,109,38],[128,146,146,146,146,146,146,146,146,129],[111,111,111,111,111,111,111,111,111,111]]}','N',5,1);

insert into TEXTE (idtxt, messagetxt, idca, idsc,idtt) values
       (1, 'Bienvenue\nVous êtes enfermé.e chez vous mais vous voulez absolument vous échapper prendre l''air! Saurez vous vous libérer?',NULL,1,1),
       (2, 'Bravo vous avez réussi!',NULL,1,2),
       (3, 'Dommage vous avez perdu!',NULL,1,3),
       (4, 'Vous êtes dans votre maison, la porte est fermée par un cadenas. Trouvez ce cadenas et le code qui permet de l''ouvrir',1,NULL,1),
       (5, 'Vous avez ouvert la porte, un tourbillon vous emporte!!!',1,NULL,2),
       (6, 'Vous n''avez pas réussi à sortir de votre maison, vous y restez coincé.e',1,NULL,3),
       (7, 'Vous arrivez dans une forêt maléfique, pour gagner il faut arriver à en sortir!',2,NULL,1),
       (8, 'Bravo vous sortez de cette forêt maléfique!',2,NULL,2),
       (9, 'Vous restez coincé.e dans cette forêt à jamais!',2,NULL,3);

insert into SITUER (idca,iden,lig,col) values (1,1,3,1),(1,2,7,3),(1,5,1,7),(2,3,4,9),(2,4,4,2),(2,6,6,1);
insert into PARTICIPER (idca, idsc, numordre) values  (1,1,1),(2,1,2);
insert into PARTIE (idpa,datedebutpa,tpsresolution,gagne,idut,idsc) values
       (1,STR_TO_DATE('21/04/2021:14:05','%d/%m/%Y:%H:%i'),'0:09','O',1,1),
       (2,STR_TO_DATE('21/04/2021:14:35','%d/%m/%Y:%H:%i'),'0:10','N',1,1);
       
