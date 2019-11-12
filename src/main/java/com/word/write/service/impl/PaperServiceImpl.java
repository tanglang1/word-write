package com.word.write.service.impl;

import com.word.write.dao.PaperMapper;
import com.word.write.pojo.Paper;
import com.word.write.pojo.StuClass;
import com.word.write.service.PaperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("paperServiceImpl")
public class PaperServiceImpl implements PaperService {
    @Resource
    private PaperMapper paperMapper;
    @Override
    public int addPaperService(Paper paper) {
        return paperMapper.addPaper(paper);
    }

    @Override
    public int updPaperService(Paper paper) {
        return paperMapper.updPaper(paper);
    }

    @Override
    public int delPaperService(String pnum) {
        return paperMapper.delPaper(pnum);
    }

    @Override
    public int findPaperCountService(String pnum, String pdateStart, String pdateEnd,Integer classid, String username,Integer isidentify) {
        return paperMapper.findPaperCount(pnum,pdateStart,pdateEnd,classid,username,isidentify);
    }

    @Override
    public List<Paper> findPaperService(String pnum, String pdateStart, String pdateEnd, Integer classid, String username,Integer isidentify, int pageStart, int pageSize) {
        return paperMapper.findPaper(pnum,pdateStart,pdateEnd,classid,username,isidentify ,pageStart,pageSize);
    }

    @Override
    public List<StuClass> findStuClassService() {
        return paperMapper.findStuClass();
    }

    @Override
    public int findWordaCountService() {
        return paperMapper.findWordaCount();
    }

    @Override
    public Paper findPaperByIdService(Integer pid) {
        return paperMapper.findPaperById(pid);
    }
}
