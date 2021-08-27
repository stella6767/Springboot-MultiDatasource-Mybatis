package net.lunalabs.central.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.lunalabs.central.domain.mysql.measuredata.MeasureData;
import net.lunalabs.central.web.dto.CMRespDto;

@RequiredArgsConstructor
@RestController
public class MeasureDataController {
	
	//일단은 mysql 기준
	
	private static final Logger log = LoggerFactory.getLogger(MeasureDataController.class);

//    @GetMapping("/measureData")
//    public CMRespDto<?> findById(@RequestBody MeasureData measureData){
//
//        log.info("측정정보 뿌리기");
//
////        if(details != null){
////            id = details.getUser().getId();
////        }
////        Page<Post> posts = postService.전체찾기(id, pageable);
////        return new CMRespDto<>(1, "게시글리스트 불러오기", posts);
//\
//    }

	
	
	
	
}
