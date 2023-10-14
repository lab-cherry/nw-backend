package lab.cherry.nw.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Common {

    public static String getClientIP(HttpServletRequest request) {
        String clientIp = null;
        boolean isIpInHeader = false;

        List<String> headerList = new ArrayList<>();
        headerList.add("X-Forwarded-For");
        headerList.add("HTTP_CLIENT_IP");
        headerList.add("HTTP_X_FORWARDED_FOR");
        headerList.add("HTTP_X_FORWARDED");
        headerList.add("HTTP_FORWARDED_FOR");
        headerList.add("HTTP_FORWARDED");
        headerList.add("Proxy-Client-IP");
        headerList.add("WL-Proxy-Client-IP");
        headerList.add("HTTP_VIA");    
        headerList.add("IPV6_ADR");

        for (String header : headerList) {
            clientIp = request.getHeader(header);
            if (StringUtils.hasText(clientIp) && !clientIp.equals("unknown")) {
                isIpInHeader = true;
                break;
            }
        }

        if (!isIpInHeader) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    public static List<Sort.Order> getOrder(String[] sort) {

        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        return orders;
    }


    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
    
	public static String getFileExtension(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return ""; // 파일이 없을 경우 빈 문자열 반환
		}

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null) {
			return ""; // 원본 파일 이름이 없을 경우 빈 문자열 반환
		}

		int lastDotIndex = originalFilename.lastIndexOf(".");
		if (lastDotIndex >= 0) {
			// 마지막 점 이후의 문자열을 추출하여 소문자로 반환
			return originalFilename.substring(lastDotIndex + 1).toLowerCase();
		} else {
			return ""; // 확장자가 없을 경우 빈 문자열 반환
		}
	}
}