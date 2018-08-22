package com.chequer.tendermint.support.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class ApiError {

	@JsonProperty("message")
	@NonNull
	private String message;

	@JsonProperty("requiredKey")
	private String requiredKey;

	private String type;

	private int code;
}
