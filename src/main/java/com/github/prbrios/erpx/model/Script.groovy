import com.github.prbrios.erpx.model.*

def methodMissing(String name, args) {
	def script = new Script(wgscript, this.binding, name)
	if (script.init()) {
		if(script.executeWithArgs(args)) {
			return script.getResult()
		} else {
			return null
		}
	} else {
		throw new RuntimeException("Nao encontrado metódo: " + name);
	}
}