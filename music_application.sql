-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost:3306
-- Thời gian đã tạo: Th4 06, 2024 lúc 01:07 AM
-- Phiên bản máy phục vụ: 8.0.30
-- Phiên bản PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `music_application`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `auth`
--

CREATE TABLE `auth` (
  `id` int UNSIGNED NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(40) NOT NULL,
  `role` enum('user','artist','admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'user',
  `image` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `auth`
--

INSERT INTO `auth` (`id`, `email`, `password`, `role`, `image`, `fullname`, `age`, `description`, `token`, `status`, `created_at`) VALUES
(1, 'user1@gmail.com', 'fa8d14e5d6fa7923e5911af3f12aa560a86510a9', 'user', 'avatar1.png', 'Artist 1', 1, NULL, NULL, 'active', '2024-02-17 14:50:41'),
(2, 'auth2@gmail.com', '12345', 'artist', 'avatar2.png', 'Artist 2', NULL, NULL, '', 'active', '2024-02-23 17:00:00'),
(3, 'auth3@gmail.com', '8cb2237d0679ca88db6464eac60da96345513964', 'artist', 'avatar4.png', 'Artist 3', NULL, NULL, '', 'active', '2024-02-23 17:01:00'),
(4, 'congnam@gmail.com', '209d5fae8b2ba427d30650dd0250942af944a0c9', 'artist', 'congnam.jpg', 'Bui Cong Nam', 10, 'Where Imagination Takes Flight, Artistry Comes Alive', '', 'active', '2024-02-25 09:07:52'),
(5, 'user1@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'user', 'avatar3.jpg', 'User', 12, NULL, '', 'active', '2024-03-02 07:35:00'),
(7, 'admin@gmail.com', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', NULL, 'Admin', 22, NULL, NULL, 'active', '2024-03-10 03:14:32'),
(10, 'hoang@gmail.com', '4ed891acfaa35b4c37a90d7f803eeb7a4e9810aa', 'artist', 'avatar4.png', 'Hoang (handsome)', 22, NULL, 'fd76fc55-3711-40de-9634-0a53439128ce', 'active', '2024-03-16 04:42:20'),
(11, 'user@gmail.com', '12dea96fec20593566ab75692c9949596833adc9', 'user', NULL, 'User', 18, NULL, NULL, 'active', '2024-03-22 06:41:00'),
(14, 'hadat@gmail.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'user', NULL, 'hadat', 18, NULL, NULL, 'active', '2024-03-30 02:23:23'),
(15, 'huyhoangtran281@gmail.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'artist', NULL, 'hoang', 15, NULL, 'aa50f4de-1cdd-43b7-ae9a-2489fba95a06', 'active', '2024-03-30 02:24:50');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `favourites_playlist`
--

CREATE TABLE `favourites_playlist` (
  `id` int UNSIGNED NOT NULL,
  `user_id` int UNSIGNED NOT NULL,
  `playlist_id` int UNSIGNED NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `favourites_playlist`
--

INSERT INTO `favourites_playlist` (`id`, `user_id`, `playlist_id`, `create_at`) VALUES
(1, 5, 1, '2024-03-02 07:36:01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `favourites_track`
--

CREATE TABLE `favourites_track` (
  `id` int UNSIGNED NOT NULL,
  `user_id` int UNSIGNED NOT NULL,
  `track_id` int UNSIGNED NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `favourites_track`
--

INSERT INTO `favourites_track` (`id`, `user_id`, `track_id`, `create_at`) VALUES
(2, 5, 9, '2024-03-02 07:35:45'),
(12, 10, 2, '2024-03-20 07:39:57'),
(14, 5, 5, '2024-03-21 16:25:27'),
(15, 10, 3, '2024-03-22 07:13:14'),
(17, 10, 36, '2024-03-26 03:38:37'),
(18, 11, 3, '2024-03-29 08:41:51'),
(19, 11, 5, '2024-03-29 08:41:54'),
(20, 3, 2, '2024-03-29 17:04:51'),
(21, 15, 4, '2024-03-30 02:26:52');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `genre_track`
--

CREATE TABLE `genre_track` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` enum('active','inactive') NOT NULL DEFAULT 'active',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `genre_track`
--

INSERT INTO `genre_track` (`id`, `name`, `status`, `create_at`) VALUES
(1, 'Pop', 'active', '2024-02-16 15:27:40'),
(2, 'Young music', 'active', '2024-02-21 13:06:23'),
(3, 'Love', 'active', '2024-02-21 13:29:31'),
(4, 'Rock', 'active', '2024-02-21 14:01:37');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `package`
--

CREATE TABLE `package` (
  `id` int NOT NULL,
  `package_name` varchar(255) NOT NULL,
  `package_price` decimal(10,2) NOT NULL,
  `validity` int NOT NULL,
  `status` enum('active','inactive') NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `package`
--

INSERT INTO `package` (`id`, `package_name`, `package_price`, `validity`, `status`, `created_at`) VALUES
(1, 'Mini', 5.00, 20, 'active', '2024-03-20 08:56:11'),
(2, 'Student', 7.00, 30, 'active', '2024-03-20 08:56:11');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `package_auth`
--

CREATE TABLE `package_auth` (
  `id` int NOT NULL,
  `package_id` int NOT NULL,
  `auth_id` int UNSIGNED NOT NULL,
  `expiration_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `package_auth`
--

INSERT INTO `package_auth` (`id`, `package_id`, `auth_id`, `expiration_date`) VALUES
(10, 2, 15, '2024-04-29');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `playlist`
--

CREATE TABLE `playlist` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `total_track` int NOT NULL,
  `auth_id` int UNSIGNED NOT NULL,
  `vip` tinyint(1) NOT NULL DEFAULT '0',
  `status` enum('active','inactive') NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `playlist`
--

INSERT INTO `playlist` (`id`, `name`, `image`, `description`, `total_track`, `auth_id`, `vip`, `status`, `created_at`) VALUES
(1, 'PlayList1', '1.jpg', 'abcd', 2, 1, 0, 'active', '2024-03-02 07:32:06'),
(2, 'PlayList2', '1.jpg', 'abcd', 1, 1, 0, 'active', '2024-03-02 07:32:06'),
(3, 'PlayList3', '1.jpg', 'abcd', 2, 1, 0, 'active', '2024-03-02 07:32:06'),
(4, 'PlayList4', '1.jpg', 'abcd', 2, 1, 0, 'active', '2024-03-02 07:32:06'),
(5, 'PlayListABC', '1.jpg', 'abcd', 2, 1, 0, 'inactive', '2024-03-02 07:32:06'),
(29, 'Foreign music', 'abumImg.jpg', 'No desc', 2, 1, 1, 'active', '2024-03-23 03:06:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `records`
--

CREATE TABLE `records` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `path` varchar(50) NOT NULL,
  `auth_id` int UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `records`
--

INSERT INTO `records` (`id`, `name`, `path`, `auth_id`, `created_at`) VALUES
(1, 'ban ghi 1', '10/ban ghi 1.mp3', 10, '2024-03-20 03:26:54'),
(13, 'hello', '15/hello.mp3', 15, '2024-03-30 02:38:19');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `track`
--

CREATE TABLE `track` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(100) NOT NULL,
  `duration` time NOT NULL,
  `genre_id` int UNSIGNED DEFAULT NULL,
  `singers` varchar(200) NOT NULL,
  `lyrics` text,
  `music_listener` int NOT NULL DEFAULT '0',
  `image` varchar(100) NOT NULL,
  `file_path` varchar(100) NOT NULL,
  `vip` tinyint(1) NOT NULL DEFAULT '0',
  `status` enum('active','inactive') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `track`
--

INSERT INTO `track` (`id`, `name`, `duration`, `genre_id`, `singers`, `lyrics`, `music_listener`, `image`, `file_path`, `vip`, `status`, `created_at`) VALUES
(2, 'Nho mai chuyen di nay', '00:03:51', 2, 'Bui Cong Nam, Wren Evans, Tien Luat, Dieu Nhi, Cam', 'Ban chan kia lau nay chua mot lan rong choi tung bay\nChua mot lan thay con gio mat den vay\nThay noi noi la nhieu thu moi la mot the gioi chua tung nghi toi\nPhai chang da lau roi ta chang nghi ngoi\nBan chan kia hom nay ra ngoai rong choi bay tung bay. Di va feel theo nhung cau hat dem ngay\nTrai tim nhu nhe nhang mo ra giua bau troi bao la\nGiua bien khoi va hoa la\n[Chorus] Nhung chang duong minh di qua, nhung con nguoi o noi xa\nToi se ghi het, cat het, giu het de mai sau nay khi mo ra\nThay rang hom qua minh da song that tha\nDa chang hoang phi ngay thanh xuan cua chinh ta\nSe nho mai hanh trinh noi day, nho phut giay nho moi ngay\nNho nhung khung troi ma minh da co o do voi gio va may\nDoi la nhung chuyen di, chuyen di nao thi cung dang nho\nVa roi mot ngay nao toi se nho, nho mai chuyen di nay\n[Verse] Da bao nhieu lau bao lau ke tu lan dau toi di\nDi qua bao noi xa xoi thay duoc co duoc nhung gi\nVan con ngay tho, van con mong mo\nHay truong thanh hon tung gio\nDa bao nhieu lau bao lau chua trao mot loi yeu thuong\nDi qua bao noi xa xoi hoa ra de toi thay duoc\nMinh con ngo nghe, minh con say me\nThi ra minh yeu doi nhu the.', 0, 'nho_mai_chuyen_di_nay.jpg', 'NhoMaiChuyenDiNay.mp3', 0, 'active', '2024-02-24 04:44:19'),
(3, '11:11', '00:04:11', 2, 'MiiNa, RIN9, DREAMeR', 'Chorus\nVua 11 gio 11 phut\nDong ho da do chuong\nLieu nguoi co nho em\nNhu cai cach em nho anh?\nDoi mong mai loi to tinh\nMa coz sao anh lang thinh?\nNoi ra loi yeu em kho vay sao?\nPhai lam sao?\n\nVerse 1\nU thi tuoi minh quen nhau cung da lau roi\nChang phai hen ho ma chi la ban be thoi\nDoi khi chuyen tro, doi khi rep story nhau\nLau lau gap mat buon may cau ai ngo dau\n\nPre-Chorus\nMot chieu nang nhat\nTu dung trai tim em nhu ron vang\nLuc anh nhin may bay nhe nhe, diu hiu gio them diu dang\nVo tinh tay nam tay ngan cam xuc chiem lap day\nA thi ra la em da yeu tu hom ay\n\nVerse 2\nAnh cung chang biet minh phai noi ra nhung gi\nDe tam tu minh gui toi em tron ven y\nNhieu lan muon mo loi\nMa lai lap bap danh thoi\nDan long som thoi roi hai ta se thanh doi\n\nEm nhu bong hoa tuoi nhat trong khu vuon\nMau mat em nhu anh ban mai con dong suong\nEm di dau loanh quanh de lac vao tim anh\nOi tinh yeu la gi? Can you tell me please?\n\nBridge\nTuong tu nhu mot loai benh nan y\nThoi gian cap bach khong the lang phi\nChan chu chi anh hay mau mau den chua lanh cho em di\nDung de em mot minh lau khoe mi\n\nFinal Chorus\nVua 11 gio 11 phut\nDien thoai em khe rung\nNhan dong tin cua anh\nLam tim em dap rat nhanh\nThi ra vu tru deu nghe\nMoi uoc ao em gui den\nDa mang nguoi em yeu den gan ben\nVa anh da noi\n\nCoda\n(Trong tim anh bao nam thang qua chi co moi mot nguoi\nNhung do lau nay so tu choi nen chang the mo loi)\nAnh noi hai ta hay hen ho thoi', 0, 'music_11-11.jpg', '11h11p.mp3', 0, 'active', '2024-02-21 13:14:11'),
(4, 'Bat tinh yeu len', '00:03:23', 2, 'Hoa Minzy, Tang Duy Tan', 'Rot mat ngot vao tai em, tat anh den bat tinh yeu len,\nSao cang say anh lai cang yeu em.\nNoi that long nguoi cho em, neu thich roi thi dua tay len,\nCho nen de trong long, chang vui dau.\n\n235 anh dang roi nhip, cham thoi em oi anh khong theo kip, em.\nMa em dang yeu hay dang treu dua, vi trong tim anh chi co ten cua em.\n\nMuon noi voi anh nhung em e ngai, chi mat may giay con tim lo dai,\nEm roi vao trong mo mong, nguoi biet khong?\nNeu da lo yeu xin anh hay la, ben do moi khi mi em uot nhoa,\nLau nay em chang yeu ai, nhieu the nay.\n\nRot mat ngot vao tai em, tat anh den bat tinh yeu len,\nSao cang say anh lai cang yeu em.\nNoi that long nguoi cho em, neu thich roi thi dua tay len,\nCho nen de trong long, chang vui dau.\n\nSao tim khong nghe loi, sao doi tay em dan trong tay anh roi.\nU thi la vi em da yeu, U thi la vi anh qua sieu.\nDo em hay do nguoi, do say men hay do em say nu cuoi.\nU thi danh lam theo trai tim, loi mat ngot nay co dang tin?\n\nChi can thuc gac cung nhin dong nguoi, ben em trong nhung dem mua tuon roi.\nAnh bong thay cuoc doi, a nham thay cuoc doi xanh hon.\nVa tinh yeu lon nhanh nhu thoi dua, anh cung chang biet chung nao la vua,\nDua em den vung troi, den vung troi yeu thuong.\n\nMuon noi voi anh nhung em e ngai, chi mat may giay con tim lo dai,\nEm roi vao trong mo mong, nguoi biet khong?\nNeu da lo yeu xin anh hay la, ben do moi khi mi em uot nhoa,\nLau nay em chang yeu ai, nhieu the nay.\n\nRot mat ngot vao tai em, tat anh den bat tinh yeu len,\nSao cang say anh lai cang yeu em.\nNoi that long nguoi cho em, neu thich roi thi dua tay len,\nCho nen de trong long, chang vui dau.', 0, 'music_Bat-Tinh-Yeu-Len.jpg', 'Bat-Tinh-Yeu-Len.mp3', 0, 'active', '2024-02-21 13:17:48'),
(5, 'Chua quen nguoi yeu cu', '00:05:02', 2, 'HA NHI, HUA KIM TUYEN', 'Cũng da gan ba nam, ma em van nho anh nhieu lam\nVan chua the yeu ai, van chua muon ben ai\nThe gian nay rong lon ma con tim chang to nhieu hon\nChi vua du nho mot nguoi, chi vua du thuong mot nguoi...\n\nDau phai em muon quen, la se quen, la se quen\nDau phai mong het dau, la bot dau, la bot dau.\nNhieu khi ly tri co gang manh me, nhung trai tim that su yeu duoi\nLa em giau di, chi la em giau di.\n\nDau phai yeu het tam, nguoi de tam, nguoi de tam.\nDau phai cho het di, nguoi chang di, nguoi chang di.\nChi vi doi khi yeu thuong mot ai, ta chang mong nhan lai gi het\nNgo dau may khi, tinh yeu lai khien ta hoen bo mi\n\nNho khi minh quen nhau, cung di tren chiec xe dap nau\nLa xanh con chua chin, da hen uoc doi minh...\nUoc muon doi ben nhau, ban tay nam mai chang roi dau\nMot cau noi trot loi...Roi ta mat nhau mot doi...\n\nDau phai em muon quen, la se quen, la se quen\nDau phai mong het dau, la bot dau, la bot dau.\nNhieu khi ly tri co gang manh me, nhung trai tim that su yeu duoi\nLa em giau di, chi la em giau di.\n\nDau phai yeu het tam, nguoi de tam, nguoi de tam.\nDau phai cho het di, nguoi chang di, nguoi chang di.\nChi vi doi khi yeu thuong mot ai, ta chang mong nhan lai gi het\nVa khong the quen, cung la mot cach yeu thuong goi ten.\n\nChang phai em khong the, khong the tim duoc mot ai khac.\nChi la vi anh da di de lai khoang khong kho day...\nMot minh em van tot, van tu minh diu xoa nhung ton thuong\nCho ngay trai tim em lai yeu va yeu nhieu hon nua.\n\nDau phai em muon quen, la se quen, la se quen\nDau phai mong het dau, la bot dau, la bot dau.\nNhieu khi ly tri co gang manh me, nhung trai tim that su yeu duoi\nLa em giau di, chi la em giau di.\n\nDau phai yeu het tam, nguoi de tam, nguoi de tam.\nDau phai cho het di, nguoi chang di, nguoi chang di.\nChi vi doi khi yeu thuong mot ai, ta chang mong nhan lai gi het\nNgo dau may khi , tinh yeu lai khien ta hoen bo mi.123', 0, 'music_Chua-Quen-Nguoi-Yeu-Cu.jpg', 'ChuaQuenNguoiYeuCu.mp3', 0, 'active', '2024-02-21 13:28:41'),
(6, 'I do', '00:03:23', 3, 'DUC PHUC, 911, KHAC HUNG', 'My whole world changed\nFrom the moment I met you\nAnd it would never be the same\nIt’s felt like I knew that I always love you\nFrom the moment I heard your name\nGiay phut ay that tuyet voi\nKhoanh khac ta co nhau trong doi\nDieu anh uoc muon bay gio ngay day roi\nDu cho trai dat co vo doi\nAnh van dung noi day thoi\nVan mai thoi de con tim anh cat loi\nI\'ll be by your side\nTill the day I die\nI\'ll be waiting till I hear you say I do\nSomething old something new\nSomething borrowed something blue\nI\'ll be waiting till I hear you say I do\nHay lau kho giot nuoc mat\nGat het di long roi boi\nDe anh duoc thay em luon mim cuoi\nHay luon siet chat tay nhe\nMoi kho khan cung duong dau\nMot hanh phuc giu gin cho nhau\nNow everything is perfect\nI know this love is worth it\nOur miracle in the making\nUntil this world stops turning\nI\'ll still be here waiting\nAnd waiting to make that vow that I\nI\'ll be by your side\nTill the day I die\nI\'ll be waiting till I hear you say I do\nSomething old something new\nSomething borrowed something blue\nI\'ll be waiting till I hear you say I do\nChi can minh cung ben nhau\nBao gian nan se qua mau\nAnh se mai luon che cho em\nCuoc doi mai sau\nI do love you yes I do love you\nI\'ll be waiting till hear you say I do\nCause I love you I do\nI\'ll be by your side till the day I die\nI\'ll be waiting till I hear you say I do\nSomething old something new\nSomething borrowed something blue\nI\'ll be waiting till I hear you say I do\nWe\'re shining like a diamond\nJust look at us now\nI wanna hear you say I do', 0, 'music_Em-Dong-Y.jpg', 'EmDongYIDo.mp3', 0, 'active', '2024-02-21 13:28:41'),
(7, 'DayLight', '00:03:33', 1, 'David kushner', 'Telling myself I won\'t go there\nOh, but I know that I won\'t care\nTryna wash away all the blood I\'ve spilt\nThis lust is a burden\nThat we both share\nTwo sinners can\'t atone from a lone prayer\nSouls tied, intertwined by pride and guilt\nThere\'s darkness in the distance\nFrom the way that I\'ve been living\nBut I know I can\'t resist it\nOh, I love it\nAnd I hate it at the same time\nYou and I drink the poison\nFrom the same vine\nOh, I love it\nAnd I hate it at the same time\nHidin\' all of our sins from the daylight\nFrom the daylight, runnin\' from thе daylight\nFrom the daylight, runnin\' from the daylight\nOh, I love it\nAnd I hatе it at the same time\nTellin\' myself it\'s the last time\nCan you spare any mercy\nThat you might find\nIf I\'m down on my knees again\nDeep down, way down, Lord, I try\nTry to follow your light\nBut it\'s nighttime\nPlease don\'t leave me in the end\nThere\'s darkness in the distance\nI\'m beggin\' for forgiveness\nBut I know I might resist it, oh\nOh, I love it\nAnd I hate it at the same time\nYou and I drink the poison\nFrom the same vine\nOh, I love it\nAnd I hate it at the same time\nHidin\' all of our sins from the daylight\nFrom the daylight, runnin\' from the daylight\nFrom the daylight, runnin\' from the daylight\nOh, I love it\nAnd I hate it at the same time\nOh, I love it\nAnd I hate it at the same time\nYou and I drink the poison\nFrom the same vine\nOh, I love it\nAnd I hate it at the same time\nHidin\' all of our sins from the daylight\nFrom the daylight, runnin\' from the daylight\nFrom the daylight, runnin\' from the daylight\nOh, I love it\nAnd I hate it at the same time', 0, 'DayLight.jpg', 'Daylight.mp3', 0, 'active', '2024-02-21 14:00:47'),
(8, 'Dancing With Your Ghost', '00:03:18', 4, 'Sasha Alex sloan', 'Telling myself I won\'t go there\nOh, but I know that I won\'t care\nTryna wash away all the blood I\'ve spilt\nThis lust is a burden\nThat we both share\nTwo sinners can\'t atone from a lone prayer\nSouls tied, intertwined by pride and guilt\nThere\'s darkness in the distance\nFrom the way that I\'ve been living\nBut I know I can\'t resist it\nOh, I love it\nAnd I hate it at the same time\nYou and I drink the poison\nFrom the same vine\nOh, I love it\nAnd I hate it at the same time\nHidin\' all of our sins from the daylight\nFrom the daylight, runnin\' from thе daylight\nFrom the daylight, runnin\' from the daylight\nOh, I love it\nAnd I hatе it at the same time\nTellin\' myself it\'s the last time\nCan you spare any mercy\nThat you might find\nIf I\'m down on my knees again\nDeep down, way down, Lord, I try\nTry to follow your light\nBut it\'s nighttime\nPlease don\'t leave me in the end\nThere\'s darkness in the distance\nI\'m beggin\' for forgiveness\nBut I know I might resist it, oh\nOh, I love it\nAnd I hate it at the same time\nYou and I drink the poison\nFrom the same vine\nOh, I love it\nAnd I hate it at the same time\nHidin\' all of our sins from the daylight\nFrom the daylight, runnin\' from the daylight\nFrom the daylight, runnin\' from the daylight\nOh, I love it\nAnd I hate it at the same time\nOh, I love it\nAnd I hate it at the same time\nYou and I drink the poison\nFrom the same vine\nOh, I love it\nAnd I hate it at the same time\nHidin\' all of our sins from the daylight\nFrom the daylight, runnin\' from the daylight\nFrom the daylight, runnin\' from the daylight\nOh, I love it\nAnd I hate it at the same time', 0, 'dancing.jpg', 'DancingWithYourGhost.mp3', 0, 'active', '2024-02-21 14:00:47'),
(9, 'Shout', '00:04:17', 4, 'Michael Jackson', 'Lyrics are being updated', 0, 'shout.jpg', 'Shout.mp3', 0, 'active', '2024-02-21 14:13:14'),
(36, 'TrainingSeason', '00:03:46', 1, 'DuaLipa', 'No lysrics', 0, 'abumImg.jpg', 'TrainingSeason-DuaLipa-13783807.mp3', 1, 'active', '2024-03-23 03:06:58'),
(37, 'WeCanTBeFriendsWaitForYourLove', '00:04:05', 1, 'ArianaGrande', 'No lysrics', 0, 'abumImg.jpg', 'WeCanTBeFriendsWaitForYourLove-ArianaGrande-14049190.mp3', 1, 'active', '2024-03-23 03:06:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `track_artist`
--

CREATE TABLE `track_artist` (
  `id` int UNSIGNED NOT NULL,
  `track_id` int UNSIGNED NOT NULL,
  `artist_id` int UNSIGNED NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `track_artist`
--

INSERT INTO `track_artist` (`id`, `track_id`, `artist_id`, `create_at`) VALUES
(1, 3, 1, '2024-02-24 00:11:03'),
(2, 3, 3, '2024-02-24 00:11:03'),
(3, 4, 2, '2024-02-24 00:11:03'),
(4, 5, 1, '2024-02-24 00:11:03'),
(5, 7, 3, '2024-02-24 00:11:03'),
(6, 8, 2, '2024-02-24 00:11:03'),
(7, 6, 1, '2024-02-24 00:11:03'),
(8, 6, 2, '2024-02-24 00:11:03'),
(9, 9, 2, '2024-02-24 00:11:03'),
(10, 2, 4, '2024-02-25 09:09:17'),
(37, 36, 1, '2024-03-23 03:06:58'),
(38, 37, 1, '2024-03-23 03:06:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `track_playlist`
--

CREATE TABLE `track_playlist` (
  `id` int UNSIGNED NOT NULL,
  `track_id` int UNSIGNED NOT NULL,
  `playlist_id` int UNSIGNED NOT NULL,
  `_order` int NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `track_playlist`
--

INSERT INTO `track_playlist` (`id`, `track_id`, `playlist_id`, `_order`, `create_at`) VALUES
(1, 3, 1, 0, '2024-03-02 07:32:43'),
(2, 5, 1, 0, '2024-03-02 07:32:43'),
(11, 36, 29, 1, '2024-03-23 03:06:58'),
(12, 37, 29, 2, '2024-03-23 03:06:58');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `auth`
--
ALTER TABLE `auth`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `favourites_playlist`
--
ALTER TABLE `favourites_playlist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `playlist_id` (`playlist_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `favourites_track`
--
ALTER TABLE `favourites_track`
  ADD PRIMARY KEY (`id`),
  ADD KEY `track_id` (`track_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `genre_track`
--
ALTER TABLE `genre_track`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `package`
--
ALTER TABLE `package`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `package_auth`
--
ALTER TABLE `package_auth`
  ADD PRIMARY KEY (`id`),
  ADD KEY `package_id` (`package_id`),
  ADD KEY `package_auth_id` (`auth_id`);

--
-- Chỉ mục cho bảng `playlist`
--
ALTER TABLE `playlist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `auth_id` (`auth_id`);

--
-- Chỉ mục cho bảng `records`
--
ALTER TABLE `records`
  ADD PRIMARY KEY (`id`),
  ADD KEY `auth_id` (`auth_id`);

--
-- Chỉ mục cho bảng `track`
--
ALTER TABLE `track`
  ADD PRIMARY KEY (`id`),
  ADD KEY `track_ibfk_1` (`genre_id`);

--
-- Chỉ mục cho bảng `track_artist`
--
ALTER TABLE `track_artist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `track_artist_ibfk_1` (`artist_id`),
  ADD KEY `track_artist_ibfk_2` (`track_id`);

--
-- Chỉ mục cho bảng `track_playlist`
--
ALTER TABLE `track_playlist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `track_playlist_ibfk_1` (`playlist_id`),
  ADD KEY `track_playlist_ibfk_2` (`track_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `auth`
--
ALTER TABLE `auth`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `favourites_playlist`
--
ALTER TABLE `favourites_playlist`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `favourites_track`
--
ALTER TABLE `favourites_track`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT cho bảng `genre_track`
--
ALTER TABLE `genre_track`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `package`
--
ALTER TABLE `package`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `package_auth`
--
ALTER TABLE `package_auth`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `playlist`
--
ALTER TABLE `playlist`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT cho bảng `records`
--
ALTER TABLE `records`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT cho bảng `track`
--
ALTER TABLE `track`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT cho bảng `track_artist`
--
ALTER TABLE `track_artist`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT cho bảng `track_playlist`
--
ALTER TABLE `track_playlist`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `favourites_playlist`
--
ALTER TABLE `favourites_playlist`
  ADD CONSTRAINT `favourites_playlist_ibfk_1` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `favourites_playlist_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `auth` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Các ràng buộc cho bảng `favourites_track`
--
ALTER TABLE `favourites_track`
  ADD CONSTRAINT `favourites_track_ibfk_1` FOREIGN KEY (`track_id`) REFERENCES `track` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `favourites_track_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `auth` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Các ràng buộc cho bảng `package_auth`
--
ALTER TABLE `package_auth`
  ADD CONSTRAINT `package_auth_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `auth` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `package_auth_ibfk_2` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Các ràng buộc cho bảng `playlist`
--
ALTER TABLE `playlist`
  ADD CONSTRAINT `playlist_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `auth` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Các ràng buộc cho bảng `records`
--
ALTER TABLE `records`
  ADD CONSTRAINT `records_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `auth` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Các ràng buộc cho bảng `track`
--
ALTER TABLE `track`
  ADD CONSTRAINT `track_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genre_track` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Các ràng buộc cho bảng `track_artist`
--
ALTER TABLE `track_artist`
  ADD CONSTRAINT `track_artist_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `auth` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `track_artist_ibfk_2` FOREIGN KEY (`track_id`) REFERENCES `track` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `track_playlist`
--
ALTER TABLE `track_playlist`
  ADD CONSTRAINT `track_playlist_ibfk_1` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `track_playlist_ibfk_2` FOREIGN KEY (`track_id`) REFERENCES `track` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
