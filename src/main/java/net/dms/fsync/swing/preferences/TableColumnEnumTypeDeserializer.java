package net.dms.fsync.swing.preferences;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

public class TableColumnEnumTypeDeserializer extends StdDeserializer<TableColumnEnumType> {

	private TableColumnEnumType[] tableColumnEnumTypes;

	public TableColumnEnumTypeDeserializer() {
		this((Class) null);
	}

	public TableColumnEnumTypeDeserializer(Class<?> vc) {
		super(vc);
	}

	public TableColumnEnumTypeDeserializer(TableColumnEnumType[] tableColumnEnumTypes) {
		this();
		this.tableColumnEnumTypes = tableColumnEnumTypes;
	}

	@Override
	public TableColumnEnumType deserialize(JsonParser jp, DeserializationContext ctxt)
	  throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		return Arrays.stream(tableColumnEnumTypes).filter(c -> c.name().equals(node.asText())).findFirst().orElse(null);
	}
}
