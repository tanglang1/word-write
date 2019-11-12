package com.word.write.controller;

import com.alibaba.fastjson.JSON;
import com.word.write.pojo.Worda;
import com.word.write.service.WordaService;
import com.word.write.util.ImportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("worda")
public class WordaController {
    @Autowired
    private WordaService wordaService;
    private HSSFRow ExcelOpt;

    @RequestMapping("/openFindWorda")
    public String openFind() {
        return "worda/findWorda";
    }

    @RequestMapping("/findByTwo")
    @ResponseBody
    public String findByTwo(@RequestParam(value = "wordmeaning", required = false) String wordmeaning, @RequestParam(value = "word", required = false) String word, HttpServletRequest request) {
        System.out.println(wordmeaning + "" + word);
        // 获取页面显示的条数
        int pageNum = Integer.parseInt(request.getParameter("limit")); //pageSize
        // 获取页面当前页
        int currPage = Integer.parseInt(request.getParameter("page")); //pageIndex
        // 计算数据库的分页从第几行开始取数
        int pageCount = (currPage - 1) * pageNum;
        List<Worda> list = wordaService.findWorda(wordmeaning, word, pageCount, pageNum);
        int count = wordaService.countWorda(wordmeaning, word);
        String json = JSON.toJSONString(list);
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":" + json + "}";
    }

    @RequestMapping("openAddWorda")
    public String openAddWorda() {
        return "worda/addWorda";
    }

    @RequestMapping("addWorda")
    @ResponseBody
    public String addWorda(Worda ww) {
        int count = wordaService.addWorda(ww);
        return JSON.toJSONString(count);
    }

    @RequestMapping("openUpdWorda")
    public String openUpdWorda(Model model, Integer id) {
        Worda sc = wordaService.findOneWorda(id);
        model.addAttribute("sc", sc);
        return "worda/updWorda";
    }

    @RequestMapping("updWorda")
    @ResponseBody
    public String updWorda(Worda ww) {
        int count = wordaService.updWorda(ww);
        return JSON.toJSONString(count);
    }

    @RequestMapping("delWorda")
    @ResponseBody
    public String delStuClass(Integer id) {
        int count = wordaService.delWorda(id);
        return JSON.toJSONString(count);
    }

    @RequestMapping(value = "uploadfile", method = { RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> uploadfile(@RequestParam("file") MultipartFile file,HttpServletRequest request)
            throws Exception {
        int count=0;
        if(file.isEmpty()){
            throw new Exception("文件不存在！");
        }
        InputStream in =null;
        List<List<Object>> listob = null;
        in = file.getInputStream();
        listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        System.out.println(listob.size());
        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            Worda worda = new Worda();
            worda.setWordmeaning(String.valueOf(lo.get(1)));
            worda.setWord(String.valueOf(lo.get(2)));
            count=wordaService.addWorda(worda);
            System.out.println("打印信息-->"+worda.toString());
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("code", "0");
        map.put("msg","导入数据成功");
        return map;
    }
}
