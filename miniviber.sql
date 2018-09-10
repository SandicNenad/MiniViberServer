-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2018 at 03:45 PM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `miniviber`
--

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `userOneId` int(11) NOT NULL,
  `userTwoId` int(11) NOT NULL,
  `actionUser` int(11) NOT NULL,
  `actionStatus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`userOneId`, `userTwoId`, `actionUser`, `actionStatus`) VALUES
(79, 80, 80, 53),
(79, 83, 79, 51),
(79, 84, 79, 53),
(79, 88, 79, 51),
(79, 89, 79, 53),
(79, 96, 79, 51),
(79, 98, 98, 51);

-- --------------------------------------------------------

--
-- Table structure for table `groupmembers`
--

CREATE TABLE `groupmembers` (
  `idUser` int(11) NOT NULL,
  `groupName` varchar(30) NOT NULL,
  `dateOfJoin` date NOT NULL,
  `timeOfJoin` varchar(12) NOT NULL,
  `typeOfMember` varchar(10) NOT NULL,
  `dateOfDeliveredMessage` date NOT NULL,
  `timeOfDeliveredMessage` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupmembers`
--

INSERT INTO `groupmembers` (`idUser`, `groupName`, `dateOfJoin`, `timeOfJoin`, `typeOfMember`, `dateOfDeliveredMessage`, `timeOfDeliveredMessage`) VALUES
(79, 'CODE Java', '2018-08-19', '17:53:05:568', 'member', '2018-08-20', '01:36:49:069'),
(79, 'CSGO', '2018-08-20', '01:35:39:386', 'asktojoin', '2000-01-01', '00:00:00:000'),
(79, 'NBA', '2018-08-19', '13:28:27', 'admin', '2018-08-19', '17:21:17:572'),
(79, 'Porodica Sandic', '2018-08-19', '17:47:45', 'admin', '2000-01-01', '00:00:00:000'),
(80, 'NBA', '2018-08-19', '14:15:11:896', 'member', '2000-01-01', '00:00:00:000'),
(82, 'NBA', '2018-08-19', '14:33:10:124', 'member', '2000-01-01', '00:00:00:000'),
(89, 'CSGO', '2018-08-19', '15:46:55', 'admin', '2000-01-01', '00:00:00:000'),
(89, 'NBA', '2018-08-19', '15:45:35:736', 'member', '2018-08-19', '17:20:19:825'),
(96, 'CODE Java', '2018-08-19', '17:51:57', 'admin', '2018-08-19', '18:04:48:602'),
(97, 'CODE Java', '2018-08-19', '18:03:54:242', 'member', '2000-01-01', '00:00:00:000'),
(98, 'CODE Java', '2018-08-19', '18:08:30:109', 'member', '2018-08-19', '18:11:37:709'),
(98, 'Emirova grupa', '2018-08-20', '11:37:43', 'admin', '2000-01-01', '00:00:00:000'),
(99, 'CODE Java', '2018-08-20', '11:40:31:084', 'member', '2000-01-01', '00:00:00:000'),
(99, 'Grupa Pavle', '2018-08-20', '11:18:30', 'admin', '2000-01-01', '00:00:00:000'),
(100, 'jelka grouo', '2018-08-20', '13:52:21', 'admin', '2018-08-20', '13:53:30:098'),
(101, 'jelka grouo', '2018-08-20', '13:53:12:742', 'member', '2018-08-20', '13:53:27:113');

-- --------------------------------------------------------

--
-- Table structure for table `groupmessages`
--

CREATE TABLE `groupmessages` (
  `idGroupMessage` int(11) NOT NULL,
  `senderId` int(11) NOT NULL,
  `groupName` varchar(30) NOT NULL,
  `messageBody` text NOT NULL,
  `sendDate` date NOT NULL,
  `sendTime` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupmessages`
--

INSERT INTO `groupmessages` (`idGroupMessage`, `senderId`, `groupName`, `messageBody`, `sendDate`, `sendTime`) VALUES
(1, 79, 'NBA', 'Pozdrav ljudi \n', '2018-08-19', '16:12:06:206'),
(2, 79, 'NBA', 'Jel gleda neko NBA veceras?\n', '2018-08-19', '16:12:12:886'),
(3, 89, 'NBA', 'Hej\n', '2018-08-19', '16:12:44:654'),
(4, 89, 'NBA', 'Ja bih gledao ako stignem\n', '2018-08-19', '16:12:52:014'),
(5, 79, 'NBA', 'Ok ti se javi ako stignes\n', '2018-08-19', '16:21:15:111'),
(6, 89, 'NBA', 'Javljam ja do veceras.\n', '2018-08-19', '17:21:09:816'),
(7, 97, 'CODE Java', 'Pozdrav ljudi\n', '2018-08-19', '18:04:29:752'),
(8, 79, 'CODE Java', 'Zdravo Luka\n', '2018-08-19', '18:04:43:880'),
(9, 96, 'CODE Java', 'Dobro nam ti dosao\n', '2018-08-19', '18:05:29:024'),
(10, 79, 'CODE Java', 'Opa grupa nam se bas povecala\n', '2018-08-19', '18:11:33:849'),
(11, 98, 'CODE Java', 'Zdravo ljudi, ja sam tek sada usao\n', '2018-08-19', '18:11:49:728'),
(12, 89, 'NBA', 'Ja ipak necu moci\n', '2018-08-20', '01:36:40:783'),
(13, 79, 'CODE Java', 'Je l` neko tu?\n', '2018-08-20', '01:37:12:901'),
(14, 89, 'NBA', 'nikako\n', '2018-08-20', '01:37:27:664'),
(15, 101, 'jelka grouo', 'cao cao\n', '2018-08-20', '13:53:19:977'),
(16, 100, 'jelka grouo', 'eeee\n', '2018-08-20', '13:53:24:520'),
(17, 101, 'jelka grouo', 'sta radis\n', '2018-08-20', '13:53:27:114'),
(18, 100, 'jelka grouo', 'nista ti\n', '2018-08-20', '13:53:30:098'),
(19, 100, 'jelka grouo', 'asdas\n', '2018-08-20', '13:53:32:066'),
(20, 100, 'jelka grouo', 'das\n', '2018-08-20', '13:53:32:952'),
(21, 100, 'jelka grouo', 'das\n', '2018-08-20', '13:53:34:191');

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `groupName` varchar(30) NOT NULL,
  `groupCreatorId` int(11) NOT NULL,
  `dateOfCreation` date NOT NULL,
  `timeOfCreation` time NOT NULL,
  `groupInfo` text NOT NULL,
  `groupPictureURL` varchar(34) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`groupName`, `groupCreatorId`, `dateOfCreation`, `timeOfCreation`, `groupInfo`, `groupPictureURL`) VALUES
('CODE Java', 96, '2018-08-19', '17:51:57', 'Grupa za polaznike jave\n\nPocetak predavanja februar mesec 2018 godine, zavrsetak avgust 2018', 'default'),
('CSGO', 89, '2018-08-19', '15:46:55', 'Ko hoce da igra counter strike global offensive neka se javi.\n\nIgramo utorkom od 19h', 'default'),
('Emirova grupa', 98, '2018-08-20', '11:37:43', 'poruka o informaciji o emirovoj grupi', 'default'),
('Grupa Pavle', 99, '2018-08-20', '11:18:30', 'Pavle je napravio ovu grupu', 'default'),
('jelka grouo', 100, '2018-08-20', '13:52:21', 'aaaaaaaaaaaaaa', 'default'),
('NBA', 79, '2018-08-19', '13:28:27', 'Grupa za sve ljubitelje nba lige.\n\nRazmenjujemo misljenja o igracima i ekipama, takodje pricamo o old school nba ligi i tadasnjim pravilima i herojima', 'default'),
('Porodica Sandic', 79, '2018-08-19', '17:47:45', 'Porodicna grupa za razmenu poruka.\n\nUkoliko ste na mrezi i iz nase porodice ste, posaljite zahtev za pristup grupi rado cemo vas prihvatiti\n\n:)   :)   :)', 'default');

-- --------------------------------------------------------

--
-- Table structure for table `privatemessage`
--

CREATE TABLE `privatemessage` (
  `idMessage` int(11) NOT NULL,
  `userOneId` int(11) NOT NULL,
  `userTwoId` int(11) NOT NULL,
  `senderId` int(11) NOT NULL,
  `messageStatus` varchar(10) DEFAULT NULL,
  `sendDate` date NOT NULL,
  `sendTime` time NOT NULL,
  `receivedDate` date NOT NULL,
  `receivedTime` time NOT NULL,
  `messageBody` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `privatemessage`
--

INSERT INTO `privatemessage` (`idMessage`, `userOneId`, `userTwoId`, `senderId`, `messageStatus`, `sendDate`, `sendTime`, `receivedDate`, `receivedTime`, `messageBody`) VALUES
(1, 79, 80, 79, 'Seen', '2018-08-19', '12:16:59', '2018-08-19', '12:17:14', 'Cao Milose\n'),
(2, 79, 80, 79, 'Seen', '2018-08-19', '12:17:04', '2018-08-19', '12:17:14', 'Sta radis?\n'),
(3, 79, 80, 80, 'Seen', '2018-08-19', '12:17:35', '2018-08-19', '12:17:35', 'Hej, evo nista\n'),
(4, 79, 80, 80, 'Seen', '2018-08-19', '16:34:47', '2018-08-19', '16:34:47', 'Hej video sam da si napravio grupu za NBA\n'),
(5, 79, 80, 79, 'Seen', '2018-08-19', '17:57:29', '2018-08-19', '17:57:29', 'Hajde veceras gledamo utakmicu\n'),
(6, 79, 80, 80, 'Seen', '2018-08-19', '17:57:44', '2018-08-19', '17:57:44', 'vazi\n'),
(7, 79, 84, 84, 'Delivered', '2018-08-20', '01:34:16', '2018-08-20', '01:34:16', 'cao shone\n'),
(8, 79, 84, 84, 'Delivered', '2018-08-20', '01:34:19', '2018-08-20', '01:34:19', 'Sta radis?\n'),
(9, 79, 99, 79, 'Seen', '2018-08-20', '11:11:45', '2018-08-20', '11:11:46', 'pozdrav\n'),
(10, 79, 99, 99, 'Seen', '2018-08-20', '11:11:59', '2018-08-20', '11:11:59', 'cao\n'),
(11, 79, 99, 79, 'Seen', '2018-08-20', '11:12:06', '2018-08-20', '11:12:06', 'sta radis\n');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `idUser` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `aboutMe` text NOT NULL,
  `workplace` varchar(20) NOT NULL,
  `education` varchar(20) NOT NULL,
  `town` varchar(25) NOT NULL,
  `country` varchar(25) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `dateOfRegistration` date NOT NULL,
  `typeOfUser` varchar(5) NOT NULL,
  `profilePictureURL` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `username`, `password`, `firstName`, `lastName`, `email`, `gender`, `aboutMe`, `workplace`, `education`, `town`, `country`, `dateOfBirth`, `dateOfRegistration`, `typeOfUser`, `profilePictureURL`) VALUES
(1, 'admin', 'admin', 'imeAdmina', 'prezimeAdmina', 'admin@gmail.com', 'male', 'adminAboutMe', 'adminWplace', 'adminFaculty', 'Belgrade', 'Serbia', '2018-06-06', '2018-06-07', 'admin', 'default'),
(79, 'shoninho', '', 'Nenad', 'Sandic', 'sandicnenad81@gmail.com', 'Male', 'Zdravo ja sam Nenad, \nvolim rad na racunarima, cesto gledam fudbal i nba ligu.\nTakodje volim da igram igre na racunaru\n\n:) :)', 'Code student', 'Singidunum', 'Belgrade', 'Serbia', '1981-05-06', '2018-08-19', 'user', 'default'),
(80, 'maza1000', 'm', 'Milos', 'Krsticevic', 'maza1000@gmail.com', 'Male', 'Pozdrav ljudi, \nja sam iz Beograda i volim da igram kosarku', 'Galactic games', 'Missouri college', 'Belgrade', 'Serbia', '1982-02-25', '2018-08-19', 'user', 'default'),
(81, 'pedja', 'p', 'Predrag', 'Ristic', 'pedja@yahoo.com', 'Male', 'Hej svi,\nradim kao barmen!\nTakodje vezbam cesto i volim da izlazim', 'Boho bar', 'Moc', 'Belgrade', 'Serbia', '1983-07-21', '2018-08-19', 'user', 'default'),
(82, 'sale', 's', 'Aleksandar', 'Jankovic', 'sale@yahoo.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Allied testing', 'Visa elektrotehnicka', 'Belgrade', 'Serbia', '1987-03-28', '2018-08-19', 'user', 'default'),
(83, 'kapri', 'k', 'Milos', 'Kapisoda', 'kapri@gmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)\nOvde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'BURT', 'Megatrend', 'Belgrade', 'Serbia', '1987-07-23', '2018-08-19', 'user', 'default'),
(84, 'toma', 't', 'Marko', 'Tomic', 'toma@gmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'BURT', 'FON', 'Belgrade', 'Serbia', '1987-08-07', '2018-08-19', 'user', 'default'),
(85, 'aleksandra', 'a', 'Aleksandra', 'Kapisoda', 'aleksandra@gmail.com', 'Female', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'pravnik doo.', 'Pravni fakultet', 'Belgrade', 'Serbia', '1986-04-11', '2018-08-19', 'user', 'default'),
(86, 'kole', 'k', 'Kosta', 'Petrovic', 'kole@gmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Graf doo', 'Graficki dizajner', 'Belgrade', 'Serbia', '1983-04-09', '2018-08-19', 'user', 'default'),
(87, 'cule', 'c', 'Marko', 'Cukic', 'cule@yahoo.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'JAT', 'Ekonomski fakultet', 'Belgrade', 'Serbia', '1981-07-07', '2018-08-19', 'user', 'default'),
(88, 'dado', 'd', 'Danijel', 'Lukovic', 'dado@hotmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Nezaposlen', 'Elektrotehnicka', 'Belgrade', 'Serbia', '1980-08-23', '2018-08-19', 'user', 'default'),
(89, 'eror', 'e', 'Marko', 'Eror', 'eror@gmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Code student', 'Ekonomski fakultet', 'Belgrade', 'Serbia', '1984-04-18', '2018-08-19', 'user', 'default'),
(90, 'pante', 'm', 'Marko', 'Pantelic', 'pante@gmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Doo', 'Marketing', 'Belgrade', 'Serbia', '1987-08-19', '2018-08-19', 'user', 'default'),
(91, 'jelena', 'j', 'Jelena', 'Jelenkovic', 'jelena@gmail.com', 'Female', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Doo', 'Ekonomski fakultet', 'Buenos Aires', 'Argentina', '1991-08-08', '2018-08-19', 'user', 'default'),
(92, 'sandra', 's', 'Sandra', 'Atijas', 'sandra@gmail.com', 'Female', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Komercijalna banka', 'ETF', 'Madrid', 'Spain', '1982-08-13', '2018-08-19', 'user', 'default'),
(93, 'ivana', 'i', 'Ivana', 'Sandic', 'ivana@gmail.com', 'Female', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Eco & co.', 'Arhitektonski', 'Novi Sad', 'Serbia', '1988-08-13', '2018-08-19', 'user', 'default'),
(94, 'marija', 'm', 'Marija', 'Maric', 'marija@gmail.com', 'Female', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'doo', 'Muzicki fakultet', 'Sarajevo', 'Bosnia and Herzegovina', '1994-08-05', '2018-08-19', 'user', 'default'),
(95, 'bojana', 'b', 'Bojana', 'Bojanic', 'bojana@yahoo.com', 'Female', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'doo', 'Pravni fakultet', 'Kragujevac', 'Serbia', '1988-08-04', '2018-08-19', 'user', 'default'),
(96, 'dekibogi', 'd', 'Dejan', 'Bogicevic', 'dekibogi@gmail.com', 'Male', 'Dejan predajem javu u CODE by Comtrade akademiji.\n\nBavis se programiranjem dugi niz godina, pored jave znam i javaScript, php i android development', 'Comtrade', 'PMF', 'Belgrade', 'Serbia', '1989-11-30', '2018-08-19', 'user', 'default'),
(97, 'luka', 'l', 'Luka', 'Kundovic', 'luka@gmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Code student', '', 'Belgrade', 'Serbia', '1997-06-21', '2018-08-19', 'user', 'default'),
(98, 'emir', 'e', 'Emir', 'Karaosmanovic', 'emir@yahoo.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Code student', 'ETF Podgorica', 'Kruševac', 'Serbia', '1990-12-26', '2018-08-19', 'user', 'default'),
(99, 'pavle', 'p', 'Pavle', 'Cobanovic', 'pavle@gmail.com', 'Male', 'Ovde ide tekst o meni,\no tome sta volim, sta radim u zivotu\n\nTakodje ima i  dosta o mojim hobijima, vrlinama i manama\n\n:) :) :)', 'Code student', '', 'Belgrade', 'Serbia', '1990-12-08', '2018-08-19', 'user', 'default'),
(100, 'jelka', 'j', 'jelka', 'jelkic', 'jelka', 'Female', '', 'j doo', 'j faculty', 'Innsbruck', 'Austria', '2018-08-01', '2018-08-20', 'user', 'default'),
(101, 'maki', 'm', 'www', 'www', 'maki', 'Male', 'b', 'bbbb', 'bbbb', 'Hong Kong', 'China', '2000-08-02', '2018-08-20', 'user', 'default');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`userOneId`,`userTwoId`),
  ADD KEY `userTwoId` (`userTwoId`),
  ADD KEY `actionUser` (`actionUser`);

--
-- Indexes for table `groupmembers`
--
ALTER TABLE `groupmembers`
  ADD PRIMARY KEY (`idUser`,`groupName`),
  ADD KEY `groupName` (`groupName`);

--
-- Indexes for table `groupmessages`
--
ALTER TABLE `groupmessages`
  ADD PRIMARY KEY (`idGroupMessage`),
  ADD KEY `senderId` (`senderId`),
  ADD KEY `groupName` (`groupName`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`groupName`),
  ADD UNIQUE KEY `groupName` (`groupName`),
  ADD KEY `groupCreatorId` (`groupCreatorId`);

--
-- Indexes for table `privatemessage`
--
ALTER TABLE `privatemessage`
  ADD PRIMARY KEY (`idMessage`),
  ADD KEY `userOneId` (`userOneId`),
  ADD KEY `userTwoId` (`userTwoId`),
  ADD KEY `senderId` (`senderId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `groupmessages`
--
ALTER TABLE `groupmessages`
  MODIFY `idGroupMessage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `privatemessage`
--
ALTER TABLE `privatemessage`
  MODIFY `idMessage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`userOneId`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`userTwoId`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `friends_ibfk_3` FOREIGN KEY (`actionUser`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `groupmembers`
--
ALTER TABLE `groupmembers`
  ADD CONSTRAINT `groupmembers_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `groupmembers_ibfk_2` FOREIGN KEY (`groupName`) REFERENCES `groups` (`groupName`);

--
-- Constraints for table `groupmessages`
--
ALTER TABLE `groupmessages`
  ADD CONSTRAINT `groupmessages_ibfk_1` FOREIGN KEY (`senderId`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `groupmessages_ibfk_2` FOREIGN KEY (`groupName`) REFERENCES `groups` (`groupName`);

--
-- Constraints for table `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `groups_ibfk_1` FOREIGN KEY (`groupCreatorId`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `privatemessage`
--
ALTER TABLE `privatemessage`
  ADD CONSTRAINT `privatemessage_ibfk_1` FOREIGN KEY (`userOneId`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `privatemessage_ibfk_2` FOREIGN KEY (`userTwoId`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `privatemessage_ibfk_3` FOREIGN KEY (`senderId`) REFERENCES `user` (`idUser`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
