package com.custom.common.utilities.validators;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.custom.common.utilities.constants.FailureConstants;
import com.custom.common.utilities.exception.CommonException;

public final class FileValidator {

	public FileValidator() {
		throw new IllegalStateException("ValidatorUtils class cannot be instantiated");
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(FileValidator.class);

	public static final List<String> IMAGE_TYPES = Collections
			.unmodifiableList(Arrays.asList("image/jpeg", "image/jpg", "image/png"));

	public enum InValidExtensions {
		JSP, EXE, SH, PEM, PPK, ZIP, GZIP, GZ, DMG, RAR, GIF
	}

	public static void validateFile(MultipartFile multipartFile, List<String> allowedTypes) throws CommonException {
		if (multipartFile == null || multipartFile.getSize() == 0l) {
			LOGGER.error("Empty file in request");
			throw new CommonException(FailureConstants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getFailureCode(),
					FailureConstants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getFailureMsg());
		}

		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		if (isInvalidExtension(extension)) {
			LOGGER.error("Invalid file extension - {}", extension);
			throw new CommonException(FailureConstants.FILE_TYPE_EXCEPTION.getFailureCode(),
					FailureConstants.FILE_TYPE_EXCEPTION.getFailureMsg());
		}

		Tika tika = new Tika();
		try {
			String detectedType = tika.detect(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
			LOGGER.info("Detected type : {}, Actual File Type : {}", detectedType, multipartFile.getContentType());

			if (allowedTypes.stream().noneMatch(type -> type.equalsIgnoreCase(detectedType))) {
				throw new CommonException(FailureConstants.FILE_TYPE_EXCEPTION.getFailureCode(),
						FailureConstants.FILE_TYPE_EXCEPTION.getFailureMsg());
			}
		} catch (IOException e) {
			LOGGER.error("IOException in detecting file type", e);
			throw new CommonException(FailureConstants.FILE_TYPE_EXCEPTION.getFailureCode(),
					FailureConstants.FILE_TYPE_EXCEPTION.getFailureMsg());
		}
	}

	public static boolean isInvalidExtension(String extension) {
		for (InValidExtensions invalidExtension : InValidExtensions.values()) {
			if (invalidExtension.name().equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

}
