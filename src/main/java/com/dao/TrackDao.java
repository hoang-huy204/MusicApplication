package com.dao;

import com.conection.Connect;
import com.model.Playlist;
import com.model.Track;

import com.model.TrackPlaylist;
import com.utils.Utils;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackDao {

    private Connection cn = null;
    private final Utils utils = new Utils();

    public TrackDao() {
        Connect conn = new Connect();
        this.cn = conn.getCn();
    }

    public ArrayList<Track> selectAllTrack(String _status) {
        ArrayList<Track> trackList = new ArrayList<>();
        try {
            String query = "SELECT * from track where status = ?";
            PreparedStatement prepareStm = this.cn.prepareCall(query);
            prepareStm.setString(1, _status);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Track row = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
                trackList.add(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trackList;
    }
    
    public ArrayList<Track> selectAllTrackNoVip() {
        ArrayList<Track> trackList = new ArrayList<>();
        try {
            String query = "SELECT * from track where vip = 0";
            PreparedStatement prepareStm = this.cn.prepareCall(query);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Track row = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
                trackList.add(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trackList;
    }

    public ArrayList<Track> selectAllTrackFavourite(Integer userId, String _status, Integer vip) {
        ArrayList<Track> trackList = new ArrayList<>();
        try {
            String query = "SELECT t.* from track AS t"
                    + " JOIN favourites_track AS ft ON ft.track_id = t.id"
                    + " JOIN auth AS a ON a.id = ft.user_id"
                    + " where t.status = ? AND a.id = ?";
            if (vip == 0) {
                query = "SELECT t.* from track AS t"
                    + " JOIN favourites_track AS ft ON ft.track_id = t.id"
                    + " JOIN auth AS a ON a.id = ft.user_id"
                    + " where t.status = ? AND t.vip = 0 AND a.id = ?";
            }
            PreparedStatement prepareStm = this.cn.prepareCall(query);
            prepareStm.setString(1, _status);
            prepareStm.setInt(2, userId);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Track row = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
                trackList.add(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trackList;
    }

    public ArrayList<Track> selectAllTrackPlaylistId(Integer playlistId, String _status) {
        ArrayList<Track> trackList = new ArrayList<>();
        try {
            String query = "SELECT t.* from track AS t"
                    + " JOIN track_playlist AS tp ON tp.track_id = t.id"
                    + " where t.status = ? AND tp.playlist_id = ?";
            PreparedStatement prepareStm = this.cn.prepareCall(query);
            prepareStm.setString(1, _status);
            prepareStm.setInt(2, playlistId);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Track row = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
                trackList.add(row);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trackList;
    }

    public Track selectFirstTrack(String _status) {
        Track track = null;
        try {
            String query = "SELECT * from track where status = ? LIMIT 1";
            PreparedStatement prepareStm = this.cn.prepareCall(query);
            prepareStm.setString(1, _status);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                track = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return track;
    }

    public ArrayList<Track> selectTrack(String status, Integer isVip) {
        ArrayList<Track> trackList = new ArrayList<>();
        try {
            String query = "SELECT * from track where status = ? order by rand() limit 6";
            if (isVip == 0) {
                query = "SELECT * from track where vip = 0 AND status = ? order by rand() limit 6";
            }
            
            PreparedStatement prepareStm = this.cn.prepareCall(query);
            prepareStm.setString(1, status);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Track row = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
                trackList.add(row);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return trackList;
    }

    public Integer insert_Tracks(Track track, Integer artist_Id) {
        try {
            cn.setAutoCommit(false);
            String query_check = "select * from track where name = ?";
            PreparedStatement preparedStatement_checkName = cn.prepareCall(query_check);
            preparedStatement_checkName.setString(1, track.getName());
            preparedStatement_checkName.execute();
            ResultSet resultSet = preparedStatement_checkName.getResultSet();
            if (!resultSet.next()) {
                String query = "insert into track (name,duration,genre_id,singers,lyrics,image,file_path) values (?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = this.cn.prepareCall(query);
                preparedStatement.setString(1, track.getName());
                preparedStatement.setTime(2, Time.valueOf(LocalTime.of((int) track.getDuration().toHours(), (int) track.getDuration().toMinutes() % 60, (int) track.getDuration().toSeconds() % 60)));
                if (track.getGenre_id() != null) {
                    preparedStatement.setInt(3, track.getGenre_id());
                } else {
                    preparedStatement.setNull(3, Types.INTEGER);
                }
                preparedStatement.setString(4, track.getSingers());
                preparedStatement.setString(5, track.getLyrics());
                preparedStatement.setString(6, track.getImage());
                preparedStatement.setString(7, track.getFile_path());
                preparedStatement.execute();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Integer generatedId = generatedKeys.getInt(1);
                    if (generatedId != null) {
                        String query_Insert_TrackArtist = "insert into track_artist (track_id,artist_id) values (?,?)";
                        PreparedStatement preparedStatement_Insert_TrackArtist = this.cn.prepareCall(query_Insert_TrackArtist);
                        preparedStatement_Insert_TrackArtist.setInt(1, generatedId);
                        preparedStatement_Insert_TrackArtist.setInt(2, artist_Id);
                        preparedStatement_Insert_TrackArtist.execute();
                        cn.commit();
                        return generatedId;
                    }
                }
            } else {
                cn.rollback();
                Utils.showNotification("Error", "Track name already exist", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Track> select_TrackByPlaylistId(Integer playlistId) {
        ArrayList<Track> trackList = new ArrayList<>();
        try {
            cn.setAutoCommit(false);
            String query_Select = "select track.* from track " +
                    "inner join track_playlist playlist " +
                    "on playlist.track_id = track.id " +
                    "where playlist.playlist_id = ?";
            PreparedStatement preparedStatement_selectTrack = cn.prepareCall(query_Select);
            preparedStatement_selectTrack.setInt(1, playlistId);
            ResultSet resultSet = preparedStatement_selectTrack.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                long durationMillis = resultSet.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = resultSet.getInt("genre_id");
                String singers = resultSet.getString("singers");
                String lyrics = resultSet.getString("lyrics");
                Integer music_listener = resultSet.getInt("music_listener");
                String image = resultSet.getString("image");
                String file_path = resultSet.getString("file_path");
                Timestamp timestamp_create_at = resultSet.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Track row = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
                trackList.add(row);
            }
            if (!trackList.isEmpty()) {
                cn.commit();
                return trackList;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Integer update_Track_4_Playlist(Track track, Integer auth_Id) {
        try {
            cn.setAutoCommit(false);
            if (track.getId() != null) {
                String check_nameExist = "select * from track where name = ? and id != ?";
                PreparedStatement preparedStatement_check_nameExist = cn.prepareCall(check_nameExist);
                preparedStatement_check_nameExist.setString(1, track.getName());
                preparedStatement_check_nameExist.setInt(2, track.getId());
                ResultSet resultSet = preparedStatement_check_nameExist.executeQuery();
                if (resultSet.next()) {
                    utils.showNotification("Warning!", "playlist name exists", Utils.NotificationType.WARNING, javafx.util.Duration.seconds(3));
                } else {
                    String update_Track = "update track set name = ? , duration = ? , genre_id = ? ,singers = ? ,lyrics = ?, image = ? where id = ?";
                    PreparedStatement preparedStatement_update_track = cn.prepareCall(update_Track);
                    preparedStatement_update_track.setString(1, track.getName());
                    Duration duration = track.getDuration();
                    LocalTime localTime = LocalTime.of(0, duration.toMinutesPart(), duration.toSecondsPart());
                    preparedStatement_update_track.setTime(2, Time.valueOf(localTime));
                    if (track.getGenre_id() != null) {
                        preparedStatement_update_track.setInt(3, track.getGenre_id());
                    } else {
                        preparedStatement_update_track.setNull(3, java.sql.Types.INTEGER);
                    }
                    preparedStatement_update_track.setString(4, track.getSingers());
                    preparedStatement_update_track.setString(5, track.getLyrics());
                    preparedStatement_update_track.setString(6, track.getImage());
                    preparedStatement_update_track.setInt(7, track.getId());
                    preparedStatement_update_track.execute();
                    cn.commit();
                    return null;
                }
            } else {
                return insert_Tracks(track, auth_Id);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlayListDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void delete_Track(Integer track_id) {
        try {
            cn.setAutoCommit(false);
            String query_deleteTrackArtist = "delete from track_artist where track_id = ?";
            PreparedStatement preparedStatement_deleteTrackArtist = cn.prepareCall(query_deleteTrackArtist);
            preparedStatement_deleteTrackArtist.setInt(1, track_id);
            preparedStatement_deleteTrackArtist.execute();

            String query = "delete from track where id = ?";
            PreparedStatement preparedStatement = cn.prepareCall(query);
            preparedStatement.setInt(1, track_id);
            preparedStatement.execute();

            cn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean delete_Track_playlist(Integer track_Id, boolean delete_Track) {
        try {
            cn.setAutoCommit(false);
            if (!delete_Track) {
                String delete_Track_Playlist = "delete from track_playlist where track_id = ?";
                PreparedStatement preparedStatement_delete_Track_Playlist = cn.prepareCall(delete_Track_Playlist);
                preparedStatement_delete_Track_Playlist.setInt(1, track_Id);
                preparedStatement_delete_Track_Playlist.execute();
            } else {
                String delete_Track_Playlist = "delete from track_playlist where track_id = ?";
                PreparedStatement preparedStatement_delete_Track_Playlist = cn.prepareCall(delete_Track_Playlist);
                preparedStatement_delete_Track_Playlist.setInt(1, track_Id);
                preparedStatement_delete_Track_Playlist.execute();

                String query_Delete_FavouritesTrack = "delete from favourites_track where track_id = ?";
                PreparedStatement preparedStatement_Delete_FavouritesTrack = cn.prepareCall(query_Delete_FavouritesTrack);
                preparedStatement_Delete_FavouritesTrack.setInt(1, track_Id);
                preparedStatement_Delete_FavouritesTrack.execute();

                String query_DeleteTrack = "delete from track where id = ?";
                PreparedStatement preparedStatement_DeleteTrack = cn.prepareCall(query_DeleteTrack);
                preparedStatement_DeleteTrack.setInt(1, track_Id);
                preparedStatement_DeleteTrack.execute();
            }

            cn.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Track selectTrackById(Integer idParam) {
        Track track = null;
        String query = "SELECT * from track where id = ?";
        PreparedStatement prepareStm;
        try {
            prepareStm = this.cn.prepareCall(query);
            prepareStm.setInt(1, idParam);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                track = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return track;
    }

    public ArrayList<Track> selectTracksOfArtist(Integer artistId) {
        ArrayList<Track> trackList = new ArrayList<>();
        String query = "SELECT t.* from track AS t JOIN track_artist AS ta ON ta.track_id = t.id JOIN auth AS a ON a.id = ta.artist_id where a.id = ? AND t.status = \"active\"";
        PreparedStatement prepareStm;
        try {
            prepareStm = this.cn.prepareCall(query);
            prepareStm.setInt(1, artistId);
            ResultSet rs = prepareStm.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                long durationMillis = rs.getTime("duration").toLocalTime().toNanoOfDay() / 1_000_000;
                Duration duration = Duration.ofMillis(durationMillis);
                Integer genre_id = rs.getInt("genre_id");
                String singers = rs.getString("singers");
                String lyrics = rs.getString("lyrics");
                Integer music_listener = rs.getInt("music_listener");
                String image = rs.getString("image");
                String file_path = rs.getString("file_path");
                Timestamp timestamp_create_at = rs.getTimestamp("created_at");
                LocalDateTime create_at = LocalDateTime.ofInstant(timestamp_create_at.toInstant(), ZoneId.systemDefault());
                Track track = new Track(id, name, duration, genre_id, singers, lyrics, music_listener, image, file_path, create_at);
                trackList.add(track);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrackDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trackList;
    }

    public Boolean add(Track track) {
        try {
            String query = "INSERT INTO Track(name, genre_id, singers, lyrics, duration,image,file_path) VALUE (?, ?, ?, ?, ?,?,?)";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, track.getName());
            preparedStm.setInt(2, track.getGenre_id());
            preparedStm.setString(3, track.getSingers());
            preparedStm.setString(4, track.getLyrics());
            preparedStm.setString(5, track.getDurationString());
            preparedStm.setString(6, track.getImage());
            preparedStm.setString(7, track.getFile_path());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(Track track) {
        try {
            String query = "UPDATE Track SET name = ?, genre_id = ?, singers = ?, lyrics = ?, duration = ?,image = ?,file_path = ? WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setString(1, track.getName());
            preparedStm.setInt(2, track.getGenre_id());
            preparedStm.setString(3, track.getSingers());
            preparedStm.setString(4, track.getLyrics());
            preparedStm.setString(5, track.getDurationString());
            preparedStm.setString(6, track.getImage());
            preparedStm.setString(7, track.getFile_path());
            preparedStm.setInt(8, track.getId());
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(Integer id) {
        try {
            String query = "DELETE FROM Track WHERE id = ?";
            PreparedStatement preparedStm = cn.prepareCall(query);
            preparedStm.setInt(1, id);
            Integer rowAffect = preparedStm.executeUpdate();
            if (rowAffect > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTrackCount() {
        int trackCount = 0;
        try {
            String query = "SELECT COUNT(*) FROM Track";
            PreparedStatement preparedStm = cn.prepareCall(query);
            ;
            ResultSet resultSet = preparedStm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trackCount;
    }


//    public ArrayList<TrackPlaylist> songs(){
//         ArrayList<TrackPlaylist> TrackPlaylistList = new ArrayList<>();
//           try {
//          String query = "SELECT t.id, t.name, t.duration, t.genre_id, t.singers, t.lyrics, t.music_listener, t.image, t.file_path, t.status, t.created_at " +
//                "FROM track_playlist tp " +
//                "JOIN track t ON tp.track_id = t.id " +
//                "JOIN playlist p ON tp.playlist_id = p.id";
//        PreparedStatement prepareStm = this.cn.prepareCall(query);
//        ResultSet rs = prepareStm.executeQuery();
//
//        while (rs.next()) {
//            
//        }
//        } catch (SQLException ex) {
//            Logger.getLogger(TrackDao.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//          return TrackPlaylistList ;
//    }
}
