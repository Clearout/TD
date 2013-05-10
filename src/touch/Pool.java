/*******************************************************************************
 * Copyright 2011 Mario Zechner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package touch;

import java.util.ArrayList;

public abstract class Pool<T> {
	private ArrayList<T> items = new ArrayList<T>();
	
	protected abstract T newItem();
	
	public T obtain() {
		if (items.size() == 0) 
			return newItem();
		return items.remove(0);
	}
	
	public void free(T item) {
		items.add(item);
	}
	public void clear() {
		items.clear();
	}
}
