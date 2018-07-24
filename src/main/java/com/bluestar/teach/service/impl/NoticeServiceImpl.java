package com.bluestar.teach.service.impl;

import com.bluestar.teach.dao.NoticeDao;
import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Notice;
import com.bluestar.teach.entity.NoticeDetail;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.enums.impl.Common;
import com.bluestar.teach.enums.impl.NoticeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluestar.teach.service.NoticeService;
import com.bluestar.common.utils.PageUtil;

import java.util.Date;
import java.util.List;

/**
 * 通知业务实现类
 *
 * @author Fish
 * created by 2018-05-07
 */
@Service
public class NoticeServiceImpl implements NoticeService
{
    private NoticeDao noticeDao = null;

    @Autowired
    public void setNoticeDao(NoticeDao noticeDao)
    {
        this.noticeDao = noticeDao;
    }

    /**
     * 老师获取通知，这里使用分页，一次显示 Page.ONE_PAGE_NUMBER 页数
     *
     * @param pageNumber 显示第几页
     * @return 返回分页之后的通知信息
     */
    @Override
    public AccountDto getNotices(Integer pageNumber, User user)
    {
        // 如果参数为空，则返回参数错误
        if (pageNumber == null || user == null || user.getUserId() == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<Notice> notices = noticeDao.getNoticesByUserId(user.getUserId());
        if (notices == null) // 得到的是空数据
        {
            return new AccountDto(NoticeStatus.IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(notices), Common.SUCCESS);
    }

    /**
     * 学生通过 classId 获取属于他的通知
     *
     * @param pageNumber 页数
     * @param classId 班级 id
     * @return 返回通知信息
     */
    public AccountDto getNotices(Integer pageNumber, Integer classId)
    {
        // 如果参数为空，则返回参数错误
        if (pageNumber == null || classId == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber);
        List<Notice> notices = noticeDao.getNoticesByClassId(classId);
        if (notices == null || notices.size() == 0) // 没有得到数据
        {
            return new AccountDto(NoticeStatus.IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(notices), Common.SUCCESS);
    }

    /**
     * 管理员得到所有通知
     *
     * @param pageNumber 页数
     * @return 返回通知信息
     */
    public AccountDto getNotices(Integer pageNumber)
    {
        // 如果参数为空，则返回参数错误
        if (pageNumber == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber); // 开始分页
        List<Notice> notices = noticeDao.getAllNotices();
        if (notices == null || notices.size() == 0)
        {
            return new AccountDto(NoticeStatus.IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(notices), Common.SUCCESS);
    }

	
    /**
     * 更新通知，只能更新具体通知内容，通知类不变
     * @param noticeDetail 具体通知
     * @return 更新结果
     */
	@Override
	public AccountDto updateNoticeDetailByNoticeDetailId(NoticeDetail noticeDetail) {
		if (noticeDetail == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        int num = noticeDao.updateNoticeDetailByNoticeDetailId(noticeDetail);
        //如果不是1，则更新失败
        if(num != 1) 
        {
        	return new AccountDto(Common.ERROR);
        }
        return new AccountDto(Common.UPDATE_NOTICE_SUCCESS);
	}
	
	/**
	 * 删除具体通知以及它对应的通知
	 * @param noticeDetailId 具体通知id
	 * @return 删除结果
	 */
	@Override
	public AccountDto deleteNoticeDetailAndNotice(Integer noticeDetailId) {
		if (noticeDetailId == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        int num = noticeDao.deleteNoticeByNoticeDetailId(noticeDetailId);
        //如果不是1，则删除失败
        if(num == 0) 
        {
        	return new AccountDto(Common.ERROR);
        }
        return new AccountDto(Common.DELTE_NOTICE_SUCCESS);
	}
	
	
	/**
	 * 插入通知，具体通知id是插入通知返回的
	 * @param notice 通知类
	 * @return 插入结果
	 */
	@Override
	@Transactional
	public AccountDto insertNotice(Notice notice, NoticeDetail noticeDetail) {
		 // 如果参数为空，则返回参数错误
        if (notice == null || noticeDetail == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }
        //发布时间
        noticeDetail.setDate(new Date());
        int num = noticeDao.insertNoticeDetail(noticeDetail);
        //如果不是1，则插入失败
        if(num != 1) 
        {
        	return new AccountDto(Common.ERROR);
        }
        notice.setNoticeDetailId(noticeDetail.getNoticeDetailId());
        num = noticeDao.insertNotice(notice);getClass();
        if(num != 1 )
        {
        	return new AccountDto(Common.ERROR);
        }
        return new AccountDto(Common.INSERT_NOTICE_SUCCESS);
	}
	
	/**
	 * 根据具体通知id得到具体通知的内容，用于更新
	 * @param noticeDetailId 具体通知
	 * @return 返回具体通知内容
	 */
	@Override
	public AccountDto getNoticeDetail(Integer noticeDetailId) {
		 // 如果参数为空，则返回参数错误
        if (noticeDetailId == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

       
        NoticeDetail noticeDetail = noticeDao.getNoticeDetailByNoticeDetailId(noticeDetailId);
        if (noticeDetail == null ) // 没有得到数据
        {
            return new AccountDto(NoticeStatus.IS_NULL);
        }

        return new AccountDto<>(noticeDetail, Common.SUCCESS);
	}
	
	/**
	 * 得到所有的具体通知
	 * @param pageNumber 开始分页
	 */
	@Override
	public AccountDto getAllNoticeDetails(Integer pageNumber) {
		// 如果参数为空，则返回参数错误
        if (pageNumber == null)
        {
            return new AccountDto(Common.WRONG_ARGEMENT);
        }

        PageUtil.toPage(pageNumber); // 开始分页
        List<NoticeDetail> noticeDetails = noticeDao.getAllNoticeDetails();
        if (noticeDetails == null || noticeDetails.size() == 0)
        {
            return new AccountDto(NoticeStatus.IS_NULL);
        }

        return new AccountDto<>(PageUtil.pageInfo(noticeDetails), Common.SUCCESS);
	}
	
	
}
