import mercury
import sys

param = 2300

if len(sys.argv) > 1:
        param = int(sys.argv[1])
#configurando
reader = mercury.Reader("tmr:///dev/ttyUSB0")

#setando regiao
reader.set_region("NA2")

reader.set_read_plan([1], "GEN2", read_power=param)

epcs = map(lambda tag: tag, reader.read())

tags = "" 

for tag in epcs:
	tags +=  tag.epc.decode("utf-8") + "\n"

print(tags)
