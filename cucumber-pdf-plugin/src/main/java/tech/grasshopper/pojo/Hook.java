package tech.grasshopper.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Hook {

	private List<String> output = new ArrayList<>();
	private List<Embedded> embeddings = new ArrayList<>();
	private Result result;
    private Match match;
}
