package lab.cherry.nw.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
}