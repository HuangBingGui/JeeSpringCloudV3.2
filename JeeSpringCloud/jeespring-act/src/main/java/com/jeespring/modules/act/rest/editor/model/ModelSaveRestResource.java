/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeespring.modules.act.rest.editor.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tijs Rademakers
 */
@RestController
public class ModelSaveRestResource implements ModelDataJsonConstants {

	protected static final Logger LOGGER = Logger.getLogger(ModelSaveRestResource.class);

	@Autowired
	private RepositoryService repositoryService;

	//	@Autowired
	//	private ObjectMapper objectMapper;
	protected ObjectMapper objectMapper = new ObjectMapper();

	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "/act/service/model/{modelId}/save",method ={RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
	@ResponseStatus(value = HttpStatus.OK)
	// @RequestBody MultiValueMap<String, String> values,MultiValueMap<String, String> valuesa
	public void saveModel(@PathVariable String modelId, HttpServletRequest request, HttpServletResponse response) {
		try {
			MultiValueMap<String, String> values=new LinkedMultiValueMap<>();;
			Model model = repositoryService.getModel(modelId);
			String name="",description="",json_xml="",svg_xml="";
			values.add("name",request.getParameter("name"));
			values.add("description",request.getParameter("description"));
			values.add("json_xml",request.getParameter("json_xml"));
			values.add("svg_xml",request.getParameter("svg_xml"));
			ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

			modelJson.put(MODEL_NAME, values.getFirst("name"));
			modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
			model.setMetaInfo(modelJson.toString());
			model.setName(values.getFirst("name"));

			repositoryService.saveModel(model);

			repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));

			InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
			TranscoderInput input = new TranscoderInput(svgStream);

			PNGTranscoder transcoder = new PNGTranscoder();
			// Setup output
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			TranscoderOutput output = new TranscoderOutput(outStream);

			// Do the transformation
			transcoder.transcode(input, output);
			final byte[] result = outStream.toByteArray();
			repositoryService.addModelEditorSourceExtra(model.getId(), result);
			outStream.close();

		} catch (Exception e) {
			LOGGER.error("Error saving model", e);
			throw new ActivitiException("Error saving model", e);
		}
	}
}
