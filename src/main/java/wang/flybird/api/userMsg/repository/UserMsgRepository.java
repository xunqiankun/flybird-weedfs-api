package wang.flybird.api.userMsg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import wang.flybird.api.userMsg.entity.UserMsg;
import wang.flybird.entity.FbMessage;

public interface UserMsgRepository  extends JpaRepository<FbMessage, Long>{
//	@Query(value ="SELECT "+
//						"fm.id AS msgid, "+
//						"fm.senderid AS senderid, "+
//						"fub.headimgurl AS sendericon, "+
//						"fm.sendertype AS sendertype, "+
//						"DATE_FORMAT(fm.sendtime,'%Y-%m-%d %H:%i') AS sendtime, "+
//					    "fm.msgtitle AS msgtitle, "+
//					    "'0' as  isread, "+
//					    "'' as  navurl, "+
//					    "'' as  msg "+
//					"FROM "+
//						"fb_message fm, "+
//						"fb_user_base fub "+
//					"WHERE fm.senderid = fub.fb_user_id "+
//					"AND fm.id > ?1 "+
//					"AND fm.receiverid = ?2 " +
//					" ORDER BY fm.id DESC ",
//	    nativeQuery = true)
	@Query("select new wang.flybird.api.userMsg.entity.UserMsg( "
           + " fm.id as msgid, "
           + " fm.senderid, "
           + " fub.headimgurl as sendericon, "
           + " fm.sendertype, "
           + " DATE_FORMAT(fm.sendtime,'%Y-%m-%d %H:%i') AS sendtime, "
           + " fm.msgtitle, "
           + " '0' as  isread, "
           + " '' as  msg "
		   + " ) "
           + " from FbMessage fm,FbUserBase fub "
           + " where fm.senderid = fub.fbUserId and fm.id >?1 and fm.receiverid= ?2 "
           + " order by fm.id desc")
	List<UserMsg> findByIdAndReceiverid(String id,String receiverid);
}
