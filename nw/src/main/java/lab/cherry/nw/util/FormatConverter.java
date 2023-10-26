package lab.cherry.nw.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FormatConverter {

    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try {
            json = mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return json;
    }


	public static String convertInputBytes(long bytes) {
		long kilobyte = 1024;
		long megabyte = kilobyte * 1024;
		long gigabyte = megabyte * 1024;
		long terabyte = gigabyte * 1024;

		if ((bytes >= 0) && (bytes < kilobyte)) {
			return bytes + "B";

		} else if ((bytes >= kilobyte) && (bytes < megabyte)) {
			return (bytes / kilobyte) + "KB";

		} else if ((bytes >= megabyte) && (bytes < gigabyte)) {
			return (bytes / megabyte) + "MB";

		} else if ((bytes >= gigabyte) && (bytes < terabyte)) {
			return (bytes / gigabyte) + "GB";

		} else if (bytes >= terabyte) {
			return (bytes / terabyte) + "TB";

		} else {
			return bytes + "Bytes";
		}
	}

	public static List<String> convertInputStreamToList(InputStream inputStream) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines;
	}


	public static Map<String, Integer> convertDateFormat(String _date) {

			Map<String, Integer> returnVal = new HashMap<>();

			try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				
					Date date = sdf.parse(_date);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					
					returnVal.put("year", calendar.get(Calendar.YEAR));
					returnVal.put("month", calendar.get(Calendar.MONTH) + 1); // 월은 0부터 시작하므로 1을 더해줍니다.
					returnVal.put("day", calendar.get(Calendar.DAY_OF_MONTH));
					returnVal.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
					returnVal.put("minute", calendar.get(Calendar.MINUTE));
					returnVal.put("second", calendar.get(Calendar.SECOND));
					
					return returnVal;

			} catch (ParseException e) {
					e.printStackTrace();
			}
			return returnVal;
		}
}